package com.lsy.test.system.common.constant;


/**
 * @author admin
 */

public enum ResponseCode {
    SUCCESS(ExceptionConstant.SUCCESS, "OK"),
    SYSTEM_EXCEPTION(ExceptionConstant.SYSTEM_EXCEPTION, "系统繁忙"),
    SERVICE_EXCEPTION(ExceptionConstant.SERVICE_EXCEPTION, "业务处理异常"),
    ILLEGAL_REQUEST(ExceptionConstant.ILLEGAL_REQUEST, "非法请求"),
    DATA_PERSISTENCE_FAIL(ExceptionConstant.DATA_PERSISTENCE_FAIL, "数据访问失败"),
    LOGIN_TIMEOUT(ExceptionConstant.LOGIN_TIMEOUT, "登录超时"),
    LOGIN_ERROR(ExceptionConstant.LOGIN_FAIL, "登录失败"),
    LOGIN_STOP(ExceptionConstant.LOGIN_STOP, "账号停用"),
    UNAUTHORIZED(ExceptionConstant.UNAUTHORIZED, "没有访问权限");


    private final int status;
    private final String message;

    ResponseCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static boolean containValue(Integer value) {
        if (value == null) {
            return false;
        }
        for (ResponseCode code : ResponseCode.values()) {
            if (value.equals(code.getStatus())) {
                return true;
            }
            continue;
        }
        //错误的value
        return false;
    }

    public static ResponseCode valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (ResponseCode code : ResponseCode.values()) {
            if (value.equals(code.getStatus())) {
                return code;
            }
            continue;
        }
        //错误的value
        return null;
    }
}
