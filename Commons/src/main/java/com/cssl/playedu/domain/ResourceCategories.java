package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 资源类别表(内容表)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCategories  {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 父级资源id
	 */
    private Integer parentId;
	/**
	 * 父链
	 */
    private String parentChain;
	/**
	 * 分类名
	 */
	private String name;
	/**
	 * 升序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新时间
	 */
	private Date updatedAt;
}
