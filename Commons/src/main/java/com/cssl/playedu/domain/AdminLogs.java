package com.cssl.playedu.domain;
/**
 * 管理日志表
 */

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin_logs")
public class AdminLogs {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 管理员id
     */
    private Integer adminId;
    /**
     * 管理员姓名
     */
    @TableField("admin_name")
    private String adminName;
    /**
     * 模块
     */
    private String module;
    /**
     * 请求方法标题
     */
    private String title;
    /**
     * 操作指令
     */
    private Integer opt;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求url
     */
    private String url;
    /**
     * 请求参数
     */
    private String param;
    /**
     * 返回参数
     */
    private String result;
    /**
     * IP
     */
    private String ip;
    /**
     * 地址
     */
    private String ipArea;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 创建时间
     */
    private Date createdAt;

}
