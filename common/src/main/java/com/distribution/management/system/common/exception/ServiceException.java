package com.distribution.management.system.common.exception;


import com.distribution.management.system.common.constant.ResponseCode;


/**
 * @author admin
 */
public class ServiceException extends BaseServiceException {

    public ServiceException(ResponseCode code) {
        super(code.getStatus(), code.getMessage());
    }

    public ServiceException(ResponseCode code, String message) {
        super(code.getStatus(), message);
    }

    public ServiceException(Integer code, String message) {
        super(code, message);
    }
}
