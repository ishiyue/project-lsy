package com.lsy.test.system.common.result;

import com.lsy.test.system.common.constant.ExceptionConstant;
import lombok.Data;

import java.io.Serializable;


/**
 * @author admin
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否调用成功
     */
    private boolean success;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回实体
     */
    private T data;

    /**
     * 错误代码
     */
    private int code = ExceptionConstant.SUCCESS;


    public Result() {
        this.success = true;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.msg = message;
    }

    public Result(boolean success, String message, int code) {
        this.success = success;
        this.msg = message;
        this.code = code;
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.msg = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>(false, message);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<T>(false, message, code);
    }

    public static <T> Result<T> success() {
        return new Result<T>(true, null);
    }

    public static <T> Result<T> success(T object) {
        return new Result<T>(true, null, object);
    }

}
