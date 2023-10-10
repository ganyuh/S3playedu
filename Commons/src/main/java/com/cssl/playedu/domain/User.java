package com.cssl.playedu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * salt
     */
    private String salt;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     *学分
     */
    private Integer credit;
    /**
     * 注册ip
     */
    private String createIp;
    /**
     * 注册城市
     */
    private String createCity;
    /**
     *激活【1：是，0否】
     */
    @TableField("is_active")
    private Boolean active;
    /**
     *锁定【1：是，0否】
     */
    @TableField("is_lock")
    private Boolean lock;
    /**
     *实名认证【1：是，0否】
     */
    @TableField("is_verify")
    private Boolean verify;
    /**
     *实名认证时间
     */
    private Date verifyAt;
    /**
     *设置密码【1：是，0否】
     */
    @TableField("is_set_password")
    private Boolean setPassword;
    /**
     *登录时间
     */
    private Date loginAt;
    /**
     *创建时间
     */
    private Date createdAt;
    /**
     *修改时间
     */
    private Date updatedAt;

    /**
     * user对应部门
     */
    private List<UserDepartment> userDepartment;

}
