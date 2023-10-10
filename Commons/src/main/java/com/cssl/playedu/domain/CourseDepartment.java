package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课程部门表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDepartment {
    /**
     * 主键
     */
    private Integer courseId;
    /**
     * 部门id
     */
    private Integer depId;

    /**
     *gyh 这是课程中心中的列表使用了五表联查
     * 连接Department联
     */
    private List<Department> departments;

    /**
     *gyh 这是课程中心中的列表使用了五表联查
     * 连接Course表
     */
    private List<Course> courses;
}
