package com.cssl.playedu.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Admin
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.SUCCESS, "请求成功", data);
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultCode.SUCCESS, "请求成功");
    }

    public static <T> Result<T> err() {
        return new Result<>(ResultCode.SYSTEM_ERROR, "服务器繁忙");
    }

    public static <T> Result<T> err(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> err(String message) {
        return new Result<>(ResultCode.SYSTEM_ERROR, message, null);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.REQUEST_FAILED, "请求失败");
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultCode.REQUEST_FAILED, message);
    }

    public static <T> Result<T> notFound() {
        return new Result<>(ResultCode.ERROR_NOT_FOUND, "请求资源不存在！");
    }
}
