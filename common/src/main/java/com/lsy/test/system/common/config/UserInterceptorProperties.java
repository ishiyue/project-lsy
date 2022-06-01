package com.lsy.test.system.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author admin
 */
@Configuration
@ConfigurationProperties(prefix = "interceptor")
public class UserInterceptorProperties {

    @Value("include")
    private List<String> include;

    @Value("exclude")
    private List<String> exclude;

    public List<String> getInclude() {
        return include;
    }

    public void setInclude(List<String> include) {
        this.include = include;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}
