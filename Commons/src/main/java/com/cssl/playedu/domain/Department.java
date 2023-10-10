package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 部门表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department  {
    /**
     * 主键
     */
	private Integer id;
    /**
     * 部门名称
     */
	private String name;
    /**
     * 父id
     */
	private Integer parentId;
    /**
     * 父链
     */
    private String parentChain;
    /**
     * 升序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 修改时间
     */
    private Date updatedAt;

}
