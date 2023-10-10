package com.cssl.playedu.controller.backstage;

import com.cssl.playedu.domain.Department;
import com.cssl.playedu.service.DepartmentService;
import com.cssl.playedu.vo.DepartmentMap;
import com.cssl.playedu.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 部门管理
 */
@RestController
@RequestMapping("/manage/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/getDepartmentAll")
    public Result<List<DepartmentMap>> getDepartmentAll(){
        List<DepartmentMap> list = departmentService.getDepartmentAll();
        return Result.ok(list);
    }

    /**
     * 新建部门
     */
    public Result<Object> addDepartment(@RequestBody Department department){
        //判断是否为首级

        //不是就进入查询sort最大加1 -- 不做，默认为0

        //建立时间默认为当前时间
        department.setCreatedAt(new Date());
        //修改时间默认为null
        department.setUpdatedAt(null);
        //待定
        return  null;
    }

    /**
     * 删除部门
     */
    //根据部门id删除
    @DeleteMapping("/delDepartment/{id}")
    public Result<Object> delDepartment(@PathVariable Integer id){
        boolean b = departmentService.removeById(id);
        if (b){
            return Result.ok().setMessage("删除成功");
        }else {
            return Result.fail().setMessage("删除失败");
        }
    }

    /**
     * 修改部门
     */
    @PutMapping("/delDepartment")
    public Result<Object> updDepartment(@RequestBody Department department){
        boolean flag = departmentService.updateById(department);
        if (flag){
            return Result.ok().setMessage("修改成功");
        }else {
            return Result.fail().setMessage("修改失败");
        }
    }
}
