package com.lsy.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author lsy
 */
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(){

        return WebClient.create();
    }
}
