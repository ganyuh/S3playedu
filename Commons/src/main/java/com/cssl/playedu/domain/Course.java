package com.cssl.playedu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 课程表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course  {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 课程标题
	 */
	private String title;
	/**
	 * 课程封面
	 */
	private String thumb;
	/**
	 * 课程价格
	 */
	private Integer charge;
	/**
	 * 简介
	 */
	private String shortDesc;
	/**
	 * 课时数
	 */
	private Integer classHour;
	/**
	 * 显示[1:是,0:否]
	 */
	@TableField("is_show")
	private Boolean show;
	/**
	 * 1:必修,0:选修
	 */
	@TableField("is_required")
	private Boolean required;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 修改时间
	 */
	private Date updatedAt;
	/**
	 * 删除时间
	 */
	private Date deletedAt;

	/**
	 * gyh 这是课程中心中的列表使用了五表联查
	 *连接CourseDepartment类
	 */
	private List<CourseDepartment> courseDepartments;

	/**
	 * gyh 这是课程中心中的列表使用了五表联查
	 *连接ResourceCourseCategories类
	 */
	private List<ResourceCourseCategories> resourceCourseCategories;
}
