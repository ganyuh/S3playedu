package com.cssl.playedu.handler;


import com.cssl.playedu.exception.BusinessException;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截抛出的自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleException(BusinessException e) {
        System.out.println("BusinessException: ");
        e.printStackTrace();
        return Result.err(e.getCode(), e.getMessage());
    }


    /**
     * 请求方法错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handleException(HttpRequestMethodNotSupportedException e) {
        System.out.println("HttpRequestMethodNotSupportedException: ");
        e.printStackTrace();
        return Result.err(ResultCode.METHOD_NOT_ALLOWED, "参数方法错误");
    }

    /**
     * 请求参数错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> handleException(HttpMessageNotReadableException e) {
        System.out.println("HttpMessageNotReadableException: ");
        e.printStackTrace();
        return Result.err(ResultCode.REQ_ERROR_PARAM, "参数错误");
    }

    /**
     * 处理 Shiro 登录认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result<Object> handleException(AuthenticationException e) {
        System.out.println("AuthenticationException: ");
        e.printStackTrace();
        return Result.err(ResultCode.REQUEST_FAILED, "账号或密码错误");
    }

    /**
     * 捕捉shiro的异常
     */
    @ExceptionHandler(ShiroException.class)
    public Result<Object> handleException() {
        return Result.err(ResultCode.NO_ACCESS,"您没有权限访问！");
    }

    /**
     * 处理所有未知的异常
     *
     * @param e 异常对象
     * @return 响应给前端的数据
     */
    @ExceptionHandler(Throwable.class)
    public Result<Object> handleException(Throwable e) {
        System.out.println("Throwable: ");
        e.printStackTrace();
        return Result.err("系统未知错误");
    }

}
