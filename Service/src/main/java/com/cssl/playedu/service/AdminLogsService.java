package com.cssl.playedu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.AdminLogs;
import com.cssl.playedu.mapper.AdminLogsMapper;

import java.util.List;

public interface AdminLogsService extends IService<AdminLogs> {
    List<AdminLogs> showAllAdminLogs();
    IPage<AdminLogs> selectUserByName(Page<AdminLogs> page, String adminName);

    IPage<AdminLogs> pageByName(Page<AdminLogs> page, QueryWrapper<AdminLogs> queryWrapper);

    /**
     * 管理日志界面的操作选项详情按钮
     */
    List<AdminLogs> showDetails();
}
