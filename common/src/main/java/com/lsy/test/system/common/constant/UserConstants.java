package com.lsy.test.system.common.constant;

/**
 * 用户常量信息
 *
 * @author admin
 */
public class UserConstants {

    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 存放在redis中的access token头
     */
    public static final String REDIS_ACCESS_TOKEN_USER_PREFIX = "access:token:user:id:";


    public static final Integer CODE = 200;

    public static final Integer TIME_OUT = -1000;

    public static final Integer REDIS_TIME_OUT = 1;

    public static final String XLS = ".xls";

    public static final String XLSX = ".xlsx";

    public static final String API_PREFIX = "/api/**";


    /**
     * 存放在redis中的用户权限
     */
    public static final String REDIS_USER_PERM = "passport:user:perm:";

    /**
     * 存放在redis中的用户信息
     */
    public static final String REDIS_USER_INFO = "passport:user:info:";


    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 1;

    public static final int USERNAME_MAX_LENGTH = 30;

    /**
     * 用户权限
     */
    public static final Integer PRINCIPAL_REDIS_TIME_OUT = 24;

}
