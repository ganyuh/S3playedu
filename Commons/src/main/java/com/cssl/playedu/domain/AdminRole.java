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
public class AdminRole {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 角色名
     */
    private String name;
    /**
     * slug
     */
    private String slug;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 该角色的所有权限
     */
    @TableField(exist = false)
    private List<AdminPermissions> perms;
}
