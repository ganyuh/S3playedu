package com.cssl.playedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.Course;
import com.cssl.playedu.domain.CourseDepartment;
import com.cssl.playedu.domain.ResourceCategories;
import com.cssl.playedu.domain.ResourceCourseCategories;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseService extends IService<Course> {
    /**
     *  五表联查(course 课程表,department 部门表,resource_categories 资源类别表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     */
    PageInfo<Course> showAllCourse(String title, Integer did, Integer rcid, Integer pageNum, Integer pageSize);

    /**
     *添加三表删除(course 课程表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     * 根据id删除，在上面三个表中都有course表中的id
     */
    boolean deleteCourse(Integer id);

    /**
     *添加三表新增(course 课程表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     */
    boolean insertCourse(Course course);
    boolean batchInsertCourseDepartments(List<CourseDepartment> departments);
    boolean batchInsertResourceCourseCategories(List<ResourceCourseCategories> categories);
    /**
     * 添加三表新增
     */
}
