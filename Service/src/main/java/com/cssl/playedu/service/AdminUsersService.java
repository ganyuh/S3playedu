package com.cssl.playedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.AdminUsers;

import java.util.List;

public interface AdminUsersService extends IService<AdminUsers> {

    /**
     * Login
     *
     * @param email 管理员邮箱
     * @return 返回从数据库中查询到管理员及角色
     */
    AdminUsers login(String email);

    /**
     * 查询管理角色表中数据
     * @return
     */
    List<AdminUsers> showAllAdminUsers();

    /**
     * 根据管理员姓名查询
     * @return
     */
    List<AdminUsers> getAllByName(String name);
}
