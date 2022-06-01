package com.lsy.test.system.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;


/**
 * @author admin
 */
@Component
@Aspect
@Slf4j
public class LogInAndOutServiceAop {
    /***
     * 注解端点拦截
     */
    @Pointcut("@annotation(com.lsy.test.system.common.annotation.ServerLog)")
    public void logPoint() {
    }

    @Around("logPoint()")
    public Object logOp(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] objects = proceedingJoinPoint.getArgs();
        String classPathName = method.getDeclaringClass().getName();
        String methodName = method.getName();
        log.info("[{}.{}] [request:{}]", classPathName, methodName, objects);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object object = proceedingJoinPoint.proceed();
        stopWatch.stop();
        log.info("[{}.{}] [total_time:{}s] [response:{}]", classPathName, methodName, stopWatch.getTotalTimeSeconds(), object);
        return object;
    }
}
