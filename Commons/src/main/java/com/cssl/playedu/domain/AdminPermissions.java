package com.cssl.playedu.domain;
/**
 * 管理权限表
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  AdminPermissions {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 类型
     */
    private String type;
    /**
     * 分组
     */
    private String groupName;
    /**
     * 升序
     */
    private Integer sort;
    /**
     * 权限名
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


}
