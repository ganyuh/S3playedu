package com.cssl.playedu.vo;

/**
 * 统一响应代码
 *
 * @Author : Tang
 * @Create 2023/8/28 14:48
 */
public class ResultCode {
    /**
     * 响应成功
     */
    public static final int SUCCESS = 200;

    /**
     * 文件未上传
     */
    public static final int FILE_NOT_UPLOADED = 213;
    /**
     * 文件上传未完成
     */
    public static final int UPLOAD_INCOMPLETE = 214;
    /**
     * 请求参数错误
     */
    public static final int REQ_ERROR_PARAM = 400;

    /**
     * 未登录
     */
    public static final int NOT_LOGGED_IN = 401;
    /**
     * 无访问权限
     */
    public static final int NO_ACCESS = 402;
    /**
     * 权限验证失败
     */
    public static final int ERROR_UNAUTHORIZED = 403;
    /**
     * 请求资源不存在
     */
    public static final int ERROR_NOT_FOUND = 404;
    /**
     * 不允许的方法
     */
    public static final int METHOD_NOT_ALLOWED = 405;
    /**
     * 请求失败
     */
    public static final int REQUEST_FAILED = 406;

    /**
     * 服务器未标记错误
     */
    public static final int SYSTEM_ERROR = 500;

    /**
     * 未配置 Minio
     */
    public static final int MINIO_NOT_CONFIGURED = 510;
    /**
     * 文件上传失败
     */
    public static final int FILE_UPLOAD_FAILED = 511;

    /**
     * 文件下传失败
     */
    public static final int FILE_DOWNLOAD_FAILED = 512;

    /**
     * 文件续传失败
     */
    public static final int FILE_RESUMPTION_FAILED = 513;
    /**
     * token 已过期
     */
    public static final int TOKEN_HAS_EXPIRED = 601;

    /**
     * token 解析异常 - 无效 token
     */
    public static final int TOKEN_PARSING_EXCEPTION = 602;


}
