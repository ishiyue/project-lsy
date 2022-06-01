package com.lsy.test.system.common.exception;

import lombok.Data;


/**
 * @author admin
 */
@Data
public class BaseServiceException extends RuntimeException {
    private int status;
    private String message;

    public BaseServiceException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public BaseServiceException(int status, String message, String exceptionMsg) {
        super(exceptionMsg);
        this.status = status;
        this.message = message;
    }

}
