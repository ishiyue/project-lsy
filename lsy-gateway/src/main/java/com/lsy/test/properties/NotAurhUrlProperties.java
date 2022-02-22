package com.lsy.test.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;

/**
 * @author lsy
 */
@Data
@ConfigurationProperties("lsy.gateway")
public class NotAurhUrlProperties {
    private LinkedHashSet<String> shouldSkipUrls;
}
