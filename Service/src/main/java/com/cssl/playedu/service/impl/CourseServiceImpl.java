package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.Course;
import com.cssl.playedu.domain.CourseDepartment;
import com.cssl.playedu.domain.ResourceCourseCategories;
import com.cssl.playedu.mapper.CourseMapper;
import com.cssl.playedu.service.CourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseMapper coursemapper;

    /**
     *  五表联查(course 课程表,department 部门表,resource_categories 资源类别表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     */
    @Override
    public PageInfo<Course> showAllCourse(String title, Integer did, Integer rcid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> course = coursemapper.showAllCourse(title,did,rcid);
        return new PageInfo<>(course);
    }

    /**
     *添加三表删除(course 课程表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     * 根据id删除，在上面三个表中都有course表中的id
     */
    @Override
    public boolean deleteCourse(Integer id) {
        return coursemapper.deleteCourse(id);
    }

    /**
     *添加三表新增(course 课程表,resource_course_categories 课程资源类别表,course_department 课程部门表)
     */
    @Override
    public boolean insertCourse(Course course) {
        return coursemapper.insertCourse(course);
    }

    @Override
    public boolean batchInsertCourseDepartments(List<CourseDepartment> departments) {
        return coursemapper.batchInsertCourseDepartments(departments);
    }

    @Override
    public boolean batchInsertResourceCourseCategories(List<ResourceCourseCategories> categories) {
        return coursemapper.batchInsertResourceCourseCategories(categories);
    }
    /**
     *添加三表新增
     */
}
