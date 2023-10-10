package com.cssl.playedu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录记录表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRecords implements Serializable {
    /**
     *主键
     */
    private Integer id;
    /**
     *用户id
     */
    private Integer userId;
    /**
     *JTI
     */
    private String jti;
    /**
     *登录ip
     */
    private String ip;
    /**
     *Ip解析区域
     */
    private String ipArea;
    /**
     *浏览器
     */
    private String browser;
    /**
     *浏览器版本
     */
    private String browserVersion;
    /**
     *操作系统
     */
    private String os;
    /**
     *过期时间
     */
    private String expired;
    /**
     *是否注销
     */
    @TableField("is_logout")
    private Boolean logout;
    /**
     *创建时间
     */
    private Date createdAt;

}
