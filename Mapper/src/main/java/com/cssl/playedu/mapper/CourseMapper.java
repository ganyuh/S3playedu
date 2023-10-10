package com.cssl.playedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cssl.playedu.domain.Course;
import com.cssl.playedu.domain.CourseDepartment;
import com.cssl.playedu.domain.ResourceCourseCategories;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    /**
     *  五表联查(course 课程表,department 部门表,resource_categories 资源类别表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     */
    List<Course> showAllCourse(String title,Integer did,Integer rcid);
    /**
     * 五表联查
     */






    /**
     *添加三表删除(course 课程表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     * 根据id删除，在上面三个表中都有course表中的id
     */
    boolean deleteCourse(Integer id);
    /**
     * 添加三表删除
     */






    /**
     *添加三表新增(course 课程表)
     */
    boolean insertCourse(Course course);
    /**
     *添加三表新增(course_department 课程部门表)
     */
    boolean batchInsertCourseDepartments(List<CourseDepartment> departments);
    /**
     *添加三表新增(resource_course_categories 课程资源类别表)
     */
    boolean batchInsertResourceCourseCategories(List<ResourceCourseCategories> categories);
    /**
     * 添加三表新增
     */
}
