package com.lsy.test.system.common.exception;


import com.lsy.test.system.common.annotation.ExceptionCode;
import com.lsy.test.system.common.constant.ResponseCode;
import com.lsy.test.system.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.SQLException;


/**
 * @author admin
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        String contentType = req.getHeader("Content-Type");
        String accept = req.getHeader("accept");
        //监控日志
        log.error(req.getRequestURI(), e);
        if ((contentType != null && contentType.toLowerCase().contains("application/json"))
                || (accept != null && accept.toLowerCase().contains("application/json"))) {
            if (e instanceof HttpMessageNotReadableException) {
                return Result.error(ResponseCode.ILLEGAL_REQUEST.getStatus(), e.getMessage());
            }
        }
        return Result.error(ResponseCode.SYSTEM_EXCEPTION.getStatus(), e.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public Result handleBindException(ServletRequestBindingException e) {
        return Result.error(ResponseCode.ILLEGAL_REQUEST.getStatus(), e.getMessage());
    }

    @ExceptionHandler(BaseServiceException.class)
    @ResponseBody
    public Result handleServiceException(BaseServiceException e) {
        log.error("\r\n错误编码：" + e.getStatus() + "\r\n错误信息：" + e.getMessage());
        return Result.error(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseBody
    public Result handleSQLException(Exception e) {
        log.error("Data persistence fail！", e);
        return Result.error(ResponseCode.DATA_PERSISTENCE_FAIL.getStatus(),
                ResponseCode.DATA_PERSISTENCE_FAIL.getMessage());
    }

    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    public Result bindExceptionHandler(BindException e) throws NoSuchFieldException {
        // 从异常对象中拿到错误信息
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        // 没有注解就提取错误提示信息进行返回统一错误码
        return Result.error(ResponseCode.ILLEGAL_REQUEST.getStatus(), defaultMessage);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public Result MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) throws NoSuchFieldException {
        // 从异常对象中拿到错误信息
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        // 参数的Class对象，等下好通过字段名称获取Field对象
        Class<?> parameterType = e.getParameter().getParameterType();
        // 拿到错误的字段名称
        String fieldName = e.getBindingResult().getFieldError().getField();
        Field field = null;
        try {
            field = parameterType.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            // 兼容父类类参数判断
            field = parameterType.getSuperclass().getDeclaredField(fieldName);
        }
        // 获取Field对象上的自定义注解
        ExceptionCode annotation = field.getAnnotation(ExceptionCode.class);
        // 有注解的话就返回注解的响应信息
        if (annotation != null) {
            return Result.error(annotation.value(), annotation.message());
        }
        // 没有注解就提取错误提示信息进行返回统一错误码
        return Result.error(ResponseCode.ILLEGAL_REQUEST.getStatus(), defaultMessage);
    }
}

