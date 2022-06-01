package com.lsy.test.system.common.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author admin
 */
@Configuration
@ConfigurationProperties(prefix = "redis.lock")
@Data
public class RedisLockProperties {
    @Value("${expire:10000}")
    private Long expire;
    @Value(("${retryNum:10}"))
    private Integer retryNum;
    @Value(("${retryTime:100}"))
    private Integer retryTime;
}
