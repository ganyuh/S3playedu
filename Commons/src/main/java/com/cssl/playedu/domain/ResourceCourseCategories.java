package com.cssl.playedu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课程资源类别表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCourseCategories {
    /**
     * 主键
     */
    private Integer courseId;
    /**
     * 类别id
     */
    private Integer categoryId;

    /**
     *gyh 这是课程中心中的列表使用了五表联查
     * 连接Course表
     */
    private List<Course> courses;

    /**
     *gyh 这是课程中心中的列表使用了五表联查
     * 连接ResourceCategories表
     */
    private List<ResourceCategories> resourceCategories;
}
