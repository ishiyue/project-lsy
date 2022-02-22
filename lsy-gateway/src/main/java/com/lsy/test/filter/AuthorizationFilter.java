package com.lsy.test.filter;

import com.lsy.test.properties.NotAurhUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.security.PublicKey;

/**
 * @author lsy
 */
@Component
@Slf4j
@EnableConfigurationProperties(value = NotAurhUrlProperties.class)
public class AuthorizationFilter implements GlobalFilter, Ordered, InitializingBean {

    /**
     * 请求各个微服务 不需要用户认证的URL
     */
    @Resource
    NotAurhUrlProperties notAuthUrlProperties;

    /**
     * jwt的公钥,需要网关启动,远程调用认证中心去获取公钥
     */
    private PublicKey publicKey;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String currentUrl = exchange.getRequest().getURI().getPath();
        if (shouldSkip(currentUrl)) {
            return chain.filter(exchange);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 放开不需要授权的路径
     * @param currentUrl
     * @return
     */
    private boolean shouldSkip(String currentUrl) {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String shipPath:notAuthUrlProperties.getShouldSkipUrls()){
            if(pathMatcher.match(shipPath,currentUrl)){
                return true;
            }
        }
        return false;
    }

}
