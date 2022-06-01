package com.lsy.test.system.common.config;

import com.lsy.test.system.common.aop.PermissionsServiceAop;
import com.lsy.test.system.common.exception.GlobalExceptionHandler;
import com.lsy.test.system.common.util.JwtUtils;
import com.lsy.test.system.common.util.UserUtils;
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
