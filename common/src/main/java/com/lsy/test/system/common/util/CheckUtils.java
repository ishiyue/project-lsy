package com.lsy.test.system.common.util;

import com.lsy.test.system.common.constant.ResponseCode;
import com.lsy.test.system.common.exception.ServiceException;

/***
 * @author admin
 * 校验统一抛出
 */
public class CheckUtils {

    public static void checkNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(ResponseCode.ILLEGAL_REQUEST, message);
        }
    }

    /***
     * 请求非法异常
     * @param isException
     * @param message
     */
    public static void checkIllegal(boolean isException, String message) {
        if (isException) {
            throw new ServiceException(ResponseCode.ILLEGAL_REQUEST, message);
        }
    }

    /***
     * 服务异常
     * @param isException
     * @param message
     */
    public static void checkServer(boolean isException, String message) {
        if (isException) {
            throw new ServiceException(ResponseCode.SERVICE_EXCEPTION, message);
        }
    }

    /***
     * 统一异常
     * @param isException
     * @param message
     */
    public static void check(boolean isException, ResponseCode responseCode, String message) {
        if (isException) {
            throw new ServiceException(responseCode, message);
        }
    }

}
