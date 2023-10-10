package com.cssl.playedu.controller.backstage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cssl.playedu.domain.AdminLogs;
import com.cssl.playedu.service.AdminLogsService;
import com.cssl.playedu.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manage/logs")
public class AdminLogsController {

    @Autowired
    private AdminLogsService adminLogsService;

    @GetMapping("/showAllAdminLogs")
    public Result<List<AdminLogs>> showAllAdminLogs(){

        List<AdminLogs> als = adminLogsService.showAllAdminLogs();

        return Result.ok(als);
    }

    /**
     * 查询 : 根据adminName性别查询用户列表，分页显示
     */
   public Result<IPage<AdminLogs>> test(String an, @RequestParam(required = false, defaultValue = "1") Integer curr){
       System.out.println("adminName: " + an);
       // 模拟复杂分页查询
       IPage<AdminLogs> adminLogsIPage =adminLogsService.selectUserByName(new Page<>(curr,10),an);
       System.out.println("总页数："+adminLogsIPage.getPages());
       System.out.println("总记录数："+adminLogsIPage.getTotal());
       adminLogsIPage.getRecords().forEach(System.out::println);

       /**
       QueryWrapper<AdminLogs> queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("admin_name",an);
       IPage<AdminLogs> iPage = adminLogsService.pageByName(new Page<>(curr, 10), queryWrapper);
       System.out.println("总页数"+adminLogsIPage.getPages());
       System.out.println("总记录数"+adminLogsIPage.getTotal());
       **/

       return Result.ok(adminLogsIPage);
   }

    /**
     * 管理日志界面的操作选项详情按钮
     */
    public Result<List<AdminLogs>> showDetails(){

        List<AdminLogs> als = adminLogsService.showDetails();

        return Result.ok(als);
    }
}
