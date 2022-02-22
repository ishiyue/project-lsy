package com.distribution.management.system.common.aop;

import com.distribution.management.system.common.annotation.Permissions;
import com.distribution.management.system.common.constant.ResponseCode;
import com.distribution.management.system.common.util.CheckUtils;
import com.distribution.management.system.common.util.PermissionsUtils;
import com.distribution.management.system.common.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author admin
 */
@Component
@Aspect
@Slf4j
public class PermissionsServiceAop {

    @Resource
    private UserUtils userUtils;

    /***
     * 注解端点拦截
     */
    @Pointcut("@annotation(com.distribution.management.system.common.annotation.Permissions)")
    public void permissionsPoint() {

    }

    @Before("permissionsPoint() && @annotation(permissions)")
    public void permissionsCheck(JoinPoint point, Permissions permissions) throws Throwable {
        log.info("打印切点信息:point:[{}]", point);
        Long userId = userUtils.getUserId();
        List<String> permissionList = PermissionsUtils.obtainPermission(userId);
        String permissionName = permissions.permission();
        if (!permissionList.contains(permissionName)) {
            CheckUtils.check(Boolean.TRUE, ResponseCode.UNAUTHORIZED, ResponseCode.UNAUTHORIZED.getMessage());
        }
    }
}
