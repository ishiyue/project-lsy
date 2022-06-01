package com.lsy.test.timerweb.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lsy
 * @since 2022/5/25 09:36:48
 */
@Configuration
@ConditionalOnExpression("'${zookeeper.serverList}'.length()>0")
public class ZookeeperConfig {
    /**
     * zookeeper 配置
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(@Value("${zookeeper.serverList}") String serverList,
            @Value("${zookeeper.namespace}") String namespace){
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList,namespace));
    }
}
