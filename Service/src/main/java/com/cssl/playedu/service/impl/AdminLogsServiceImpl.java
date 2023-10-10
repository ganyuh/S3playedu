package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.AdminLogs;
import com.cssl.playedu.mapper.AdminLogsMapper;
import com.cssl.playedu.service.AdminLogsService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLogsServiceImpl extends ServiceImpl<AdminLogsMapper, AdminLogs> implements AdminLogsService {

    @Autowired
    private AdminLogsMapper adminLogsMapper;

    @Override
    public List<AdminLogs> showAllAdminLogs(){

        return adminLogsMapper.showAllAdminLogs();
    }

    /**
     *
     * @param page
     * @param adminName
     * @return
     */
    public IPage<AdminLogs> selectUserByName(Page<AdminLogs> page, String adminName){

        return adminLogsMapper.selectUserByName(page,adminName);
    }
    /**
     * 简单分页查询:条件构造器QueryWrapper
      */

    public IPage<AdminLogs> pageByName(Page<AdminLogs> page, QueryWrapper<AdminLogs> queryWrapper){

        return adminLogsMapper.selectPage(page,queryWrapper);
    }

    /**
     * 管理日志界面的操作选项详情按钮
     */
    @Override
    public  List<AdminLogs> showDetails(){

        return adminLogsMapper.showDetails();
    }
}
