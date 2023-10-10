package com.cssl.playedu.exception;

/**
 * @Author : Tang
 * @CreateDate 2023/8/28 22:47
 */

import lombok.Getter;

/**
 * 系统异常类
 */
@Getter
public class SystemException extends RuntimeException {
    Integer code;

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    protected SystemException(Integer code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
