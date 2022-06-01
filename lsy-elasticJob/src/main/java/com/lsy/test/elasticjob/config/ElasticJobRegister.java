package com.lsy.test.elasticjob.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lsy.test.elasticjob.config.ElasticJobProperties.JobConf;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * Elastic-Job 初始化
 *
 * @author liu.yj
 * @since 2022/1/12 19:51
 */
@Slf4j
@Configuration
public class ElasticJobRegister implements ApplicationContextAware, ElasticJobDefinition {

    @Value("${elastic-job.group:${spring.application.name}}")
    private String group;
    @Resource
    private ElasticJobProperties properties;
    @Resource
    private ZookeeperRegistryCenter registryCenter;
    @Autowired(required = false)
    private JobEventConfiguration jobEventConfiguration;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        init(applicationContext);
    }

    private void init(ApplicationContext applicationContext) {
        Map<String, SimpleJob> simpleJobs = applicationContext.getBeansOfType(SimpleJob.class);
        Map<String, JobConf> jobConfigs = Optional.ofNullable(properties.getJobs())
                .map(present -> present.stream().collect(Collectors.toMap(JobConf::getName, e -> e)))
                .orElse(Collections.emptyMap());

        for (Map.Entry<String, SimpleJob> entry : simpleJobs.entrySet()) {
            SimpleJob simpleJob = entry.getValue();
            ElasticJobConf elasticJobConfAnnotation = simpleJob.getClass().getAnnotation(ElasticJobConf.class);
            if (elasticJobConfAnnotation == null) {
                log.warn("Elastic job-[{}] is not annotated with @ElasticJobConf.", simpleJob.getClass().getName());
                continue;
            }

            String name = elasticJobConfAnnotation.name();
            if (name.isEmpty()) {
                name = simpleJob.getClass().getSimpleName();
            }

            // 优先使用 yaml 的配置项
            Optional<JobConf> jobConf = Optional.ofNullable(jobConfigs.get(name));
            if (!jobConf.map(JobConf::getOpen).orElse(true)) {
                log.info("Elastic job-[{}] is closed by yaml configuration.", name);
                continue;
            }
            String description = jobConf.map(JobConf::getDescription)
                    .orElse(elasticJobConfAnnotation.description());

            String cron = jobConf.map(JobConf::getCron)
                    .orElse(elasticJobConfAnnotation.cron());

            boolean persistTrace = jobConf.map(JobConf::getPersistTrace)
                    .orElse(elasticJobConfAnnotation.persistTrace());

            int shardingTotalCount = jobConf.map(JobConf::getShardingTotalCount)
                    .orElse(elasticJobConfAnnotation.shardingTotalCount());

            String shardingItemParameters = jobConf.map(JobConf::getShardingItemParameters)
                    .orElse(elasticJobConfAnnotation.shardingItemParameters());

            String jobParameter = jobConf.map(JobConf::getJobParameter)
                    .orElse(elasticJobConfAnnotation.jobParameter());

            LiteJobConfiguration liteJobConfiguration = getLiteJobConfiguration(simpleJob,
                    JobConf.builder()
                            // 分布式环境下的实际作业名：group@name
                            .name(getFullJobName(group, name))
                            .description(description)
                            .cron(cron)
                            .shardingTotalCount(shardingTotalCount)
                            .shardingItemParameters(shardingItemParameters)
                            .jobParameter(jobParameter)
                            .build()
            );

            SpringJobScheduler jobScheduler;
            // 是否需要持久化执行日志
            if (persistTrace) {
                if (jobEventConfiguration == null) {
                    log.error("persist job could not find rdb configuration! try to specify a datasource.");
                    throw new NoSuchBeanDefinitionException("jobEventConfiguration");
                }
                jobScheduler = new SpringJobScheduler(
                        simpleJob, registryCenter, liteJobConfiguration, jobEventConfiguration);
            } else {
                jobScheduler = new SpringJobScheduler(
                        simpleJob, registryCenter, liteJobConfiguration);
            }
            jobScheduler.init();
            log.info("Elastic job-[{}] registered. Cron-[{}]", name, cron);
        }
    }
}
