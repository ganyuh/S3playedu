package com.cssl.playedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cssl.playedu.domain.AdminLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminLogsMapper extends BaseMapper<AdminLogs> {

    List<AdminLogs> showAllAdminLogs();

    // 通过姓名分页查询
    IPage<AdminLogs> selectUserByName(Page<AdminLogs> page, @Param("adminName") String adminName);

    /**
     * 管理日志界面的操作选项详情按钮
     */
    List<AdminLogs> showDetails();
}
