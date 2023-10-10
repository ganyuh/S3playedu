package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程附件表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseAttachment  {
    /**
     * 主键
     */
	private Integer id;
    /**
     * 课程id
     */
	private Integer courseId;
    /**
     * 升序
     */
	private Integer sort;
    /**
     * 附件名
     */
	private String title;
    /**
     * 附件类型
     */
	private String type;
    /**
     * 资源id
     */
	private Integer rid;
    /**
     * 创建时间
     */
	private Date createdAt;
}
