package com.distribution.management.system.common.config;

import com.distribution.management.system.common.aop.PermissionsServiceAop;
import com.distribution.management.system.common.exception.GlobalExceptionHandler;
import com.distribution.management.system.common.util.JwtUtils;
import com.distribution.management.system.common.util.UserUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
public class BeanConfig {
    @Bean
    public GlobalExceptionHandler globalFeignExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public UserUtils userUtils() {
        return new UserUtils();
    }

    @Bean
    public UserInterceptorProperties userInterceptorProperties() {
        return new UserInterceptorProperties();
    }


    @Bean
    public PermissionsServiceAop permissionsServiceAop() {
        return new PermissionsServiceAop();
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
    
}
