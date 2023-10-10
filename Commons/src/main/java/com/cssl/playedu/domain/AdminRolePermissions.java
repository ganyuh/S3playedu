package com.cssl.playedu.domain;
/**
 * 管理角色权限表
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRolePermissions {
    /**
     * 主键
     */
    private Integer roleId;
    /**
     * 权限id
     */
    private Integer permId;


}
