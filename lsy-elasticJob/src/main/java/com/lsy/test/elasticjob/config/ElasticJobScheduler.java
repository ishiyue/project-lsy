package com.lsy.test.elasticjob.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.JobRegistry;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lsy.test.elasticjob.config.ElasticJobProperties.JobConf;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Elastic-Job 注册任务
 *
 * @author liu.yj
 * @since 2022/1/12 20:43
 */
@Slf4j
@Configuration
public class ElasticJobScheduler implements ElasticJobDefinition {

    @Value("${elastic-job.group:${spring.application.name}}")
    private String group;
    @Resource
    private ZookeeperRegistryCenter registryCenter;
    @Autowired(required = false)
    private JobEventConfiguration jobEventConfiguration;

    public void schedule(SimpleJob simpleJob, Date triggerTime, String uniqueId) {
        schedule(simpleJob, triggerTime, uniqueId, "");
    }

    public void schedule(SimpleJob simpleJob, Date triggerTime, String uniqueId, String jobParams) {
        schedule(simpleJob, buildCron(triggerTime), uniqueId, jobParams);
    }

    public void schedule(SimpleJob simpleJob, String cron, String uniqueId) {
        schedule(simpleJob, cron, uniqueId, "");
    }

    /**
     * 自定义调度定时任务，uniqueId确保唯一
     *
     * @param simpleJob Job对象
     * @param cron
     * @param uniqueId
     * @param jobParams
     */
    public void schedule(SimpleJob simpleJob, String cron, String uniqueId, String jobParams) {
        String fullJobName = getFullJobName(group, simpleJob.getClass().getSimpleName(), uniqueId);
        LiteJobConfiguration liteJobConfiguration = getLiteJobConfiguration(simpleJob,
                JobConf.builder()
                        .name(fullJobName)
                        .cron(cron)
                        .persistTrace(true)
                        .shardingTotalCount(1)
                        .shardingItemParameters("")
                        .jobParameter(jobParams)
                        .build());

        SpringJobScheduler jobScheduler;
        if (jobEventConfiguration == null) {
            log.warn("No rdb configuration! Cannot persist trace log.");
            jobScheduler = new SpringJobScheduler(simpleJob, registryCenter, liteJobConfiguration);
        } else {
            jobScheduler = new SpringJobScheduler(simpleJob, registryCenter, liteJobConfiguration,
                    jobEventConfiguration);
        }
        jobScheduler.init();
        log.info("Elastic job-[{}] scheduled. Cron-[{}]", fullJobName, cron);
    }

    public void stop(SimpleJob simpleJob, String uniqueId) {
        JobRegistry.getInstance().shutdown(getFullJobName(group, simpleJob.getClass().getSimpleName(), uniqueId));
    }

    /**
     * 根据指定时间获取cron表达式
     *
     * @param date Date
     * @return cron: 0 0 0 1 1 ? 2021
     */
    public static String buildCron(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND)
                + " " + cal.get(Calendar.MINUTE)
                + " " + cal.get(Calendar.HOUR_OF_DAY)
                + " " + cal.get(Calendar.DAY_OF_MONTH)
                + " " + (cal.get(Calendar.MONTH) + 1)
                + " ? " + cal.get(Calendar.YEAR);
    }
}
