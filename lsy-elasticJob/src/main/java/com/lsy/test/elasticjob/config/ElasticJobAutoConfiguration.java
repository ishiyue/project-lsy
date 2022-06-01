package com.lsy.test.elasticjob.config;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lsy.test.elasticjob.config.ElasticJobProperties.ZookeeperProperties;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ES-JOB auto configuration.
 *
 * @author liu.yj
 * @since 2022/1/11 21:47
 */
@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties({ElasticJobProperties.class})
public class ElasticJobAutoConfiguration {

    /**
     * 注册中心配置
     * @param properties
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registerCenter(ElasticJobProperties properties) {
        ZookeeperProperties zookeeper = properties.getZookeeper();
        return new ZookeeperRegistryCenter(
                new ZookeeperConfiguration(zookeeper.getServerLists(), zookeeper.getNamespace()));
    }

    /**
     *  数据源 , 事件执行持久化策略
     * @param dataSource
     * @return
     */
    @Bean
    @ConditionalOnBean(DataSource.class)
    public JobEventConfiguration jobEventConfiguration(DataSource dataSource) {
        return new JobEventRdbConfiguration(dataSource);
    }

    @Bean
    public ElasticJobAPIService elasticJobAPIService(ElasticJobProperties properties) {
        return new ElasticJobAPIService(properties.getZookeeper());
    }

    @Bean
    public ElasticJobService elasticJobService(ElasticJobAPIService jobAPIService) {
        return new ElasticJobService(jobAPIService);
    }
}

