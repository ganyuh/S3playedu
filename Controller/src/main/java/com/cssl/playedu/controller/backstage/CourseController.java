package com.cssl.playedu.controller.backstage;

import com.cssl.playedu.domain.Course;
import com.cssl.playedu.domain.CourseDepartment;
import com.cssl.playedu.domain.ResourceCourseCategories;
import com.cssl.playedu.service.CourseService;
import com.cssl.playedu.vo.Result;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Configuration
@EnableTransactionManagement
@RequestMapping("/manage/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/showAllCourse")
    public Result<PageInfo<Course>> showAllCourse(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) Integer did,
                                                  @RequestParam(required = false) Integer rcid,
                                                  @RequestParam(required = false, defaultValue = "1") Integer ps,
                                                  @RequestParam(required = false, defaultValue = "10") Integer pn) {
        PageInfo<Course> course = courseService.showAllCourse(title,did,rcid,ps,pn);
        return Result.ok(course);
    }

    @DeleteMapping("/deleteCourse/{id}")
    @Transactional
    public Result<Object> deleteCourse(@PathVariable Integer id){
        boolean b = courseService.deleteCourse(id);
        return b ? Result.ok("删除成功！") : Result.err("删除失败！");
    }

    @PostMapping("/insertCourse")
    public Result<Object> insertCourse(@Param("course") Course course,
                                       @Param("departments") List<CourseDepartment> departments,
                                       @Param("categories") List<ResourceCourseCategories> categories){
        boolean b = courseService.insertCourse(course);
        if (b) {
            boolean c = courseService.batchInsertCourseDepartments(departments);
            if (c) {
                boolean d = courseService.batchInsertResourceCourseCategories(categories);
                return d ? Result.ok("添加成功！") : Result.err("添加失败！");
            }
            return Result.err("添加失败！");
        }
        return Result.err("添加失败！");
    }
}
