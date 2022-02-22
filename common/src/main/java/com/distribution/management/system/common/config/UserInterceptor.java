package com.distribution.management.system.common.config;

import com.distribution.management.system.common.constant.ResponseCode;
import com.distribution.management.system.common.exception.ServiceException;
import com.distribution.management.system.common.util.JwtUtils;
import com.distribution.management.system.common.util.UserUtils;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 */
@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private UserUtils userUtils;

    @Value("${neglect.url}")
    private String neglectUrl;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //从header中获取token
        final String cookieKey = httpServletRequest.getHeader(jwtUtils.getCookieKey());
        final String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.equalsIgnoreCase(neglectUrl)) {
            return Boolean.TRUE;
        }
        if (StringUtils.isNotBlank(cookieKey)) {
            userUtils.add(Long.valueOf(cookieKey));
            return Boolean.TRUE;
        }
        String token = getToken(httpServletRequest);
        //token为空
        if (StringUtils.isBlank(token)) {
            throw new ServiceException(ResponseCode.LOGIN_TIMEOUT, "登录超时");
        }
        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            throw new ServiceException(ResponseCode.LOGIN_TIMEOUT, "登录超时");
        }
        userUtils.add(Long.valueOf(claims.getSubject()));

        return Boolean.TRUE;
    }

    private String getToken(HttpServletRequest httpServletRequest) {
        String token = "";
        //从header中获取token
        if (StringUtils.isBlank(token)) {
            token = httpServletRequest.getHeader(jwtUtils.getCookieKey());
        }
        Cookie[] cookies = httpServletRequest.getCookies();
        List<Cookie> cookieList = Lists.newArrayList();
        if (cookies != null) {
            cookieList = Arrays.asList(cookies);
        }
        for (Cookie cookie : cookieList) {
            if (jwtUtils.getCookieKey().equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        return token;
    }

}
