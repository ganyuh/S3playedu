package com.cssl.playedu.domain;
/**
 * 管理角色表
 */

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUsers {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * Salt
     */
    private String salt;
    /**
     * 登录Ip
     */
    private String loginIp;
    /**
     * 登录时间
     */
    private String loginAt;
    /**
     * 是否禁止登录
     */
    @TableField("is_ban_login")
    private Boolean banLogin;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 管理员所有角色
     */
    @TableField(exist = false)
    private List<AdminRole> roles;
}
