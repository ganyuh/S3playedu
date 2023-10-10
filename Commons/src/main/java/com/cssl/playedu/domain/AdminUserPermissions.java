package com.cssl.playedu.domain;
/**
 * 管理用户权限表
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserPermissions {
    /**
     * 主键
     */
    private Integer adminId;
    /**
     * 角色id
     */
    private Integer roleId;

}
