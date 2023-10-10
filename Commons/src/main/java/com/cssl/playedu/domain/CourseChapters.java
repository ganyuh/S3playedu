package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程章节表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseChapters  {
    /**
     * 主键
     */
	private Integer id;
    /**
     * 课程id
     */
	private Integer courseId;
    /**
     * 章节名
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
     * 修改时间
     */
    private Date updatedAt;
}
