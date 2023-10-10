package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 课程时间表表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseHour {
    /**
     * 主键
     */
	private Integer id;
    /**
     * 课程id
     */
	private Integer courseId;
    /**
     * 章节id
     */
	private Integer chapterId;
    /**
     * 升序
     */
    private Integer sort;
    /**
     * 课时名
     */
    private String title;
    /**
     * 课时类型
     */
    private String type;
    /**
     * 资源id
     */
    private Integer rid;
    /**
     * 时长[s]
     */
    private Integer duration;
    /**
     * 创建时间
     */
    private Date createdAt;
}
