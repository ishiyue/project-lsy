/*
package com.distribution.management.system.common.config;

import com.distribution.management.system.common.util.UserUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


*/
/**
 * 拦截feign的上下文,将id放在本地环境变量里面
 *
 * @author cyh
 *//*

@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    private static final String USER_ID = "Merchant-Uid";

    @Resource
    private UserUtils userUtils;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("****************feign拦截器调用开始*******************");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (name.equalsIgnoreCase(USER_ID)) {
                        String values = request.getHeader(USER_ID);
                        log.info("values is:[{}]", values);
                        if (StringUtils.isNotEmpty(values)) {
                            userUtils.add(Long.valueOf(values));
                            requestTemplate.header(name, values);
                        }
                    }
                }
            }
        }
    }
}
*/
