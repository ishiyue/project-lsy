package com.distribution.management.system.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @author admin
 * @version 0.0.1
 * @date 2019/8/27 14:20
 */
@Configuration
public class UserInterceptorConfig extends WebMvcConfigurationSupport {

    @Resource
    private UserInterceptorProperties userInterceptorProperties;

    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns(userInterceptorProperties.getInclude())
                .excludePathPatterns(userInterceptorProperties.getExclude());
    }
}
