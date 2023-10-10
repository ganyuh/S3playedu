package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.AdminUsers;
import com.cssl.playedu.mapper.AdminUsersMapper;
import com.cssl.playedu.service.AdminUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AdminUsersServiceImpl extends ServiceImpl<AdminUsersMapper, AdminUsers> implements AdminUsersService {

    @Autowired
    private AdminUsersMapper adminUsersMapper;

    @Override
    public AdminUsers login(String email) {
        AdminUsers admin = adminUsersMapper.queryAdminByEmail(email);
//        System.out.println("service login admin = " + admin);
        return admin;
    }

    /**
     * 查询AdminUsers表中数据
     *
     * @return
     */
    @Override
    public List<AdminUsers> showAllAdminUsers() {

        return adminUsersMapper.showAllAdminUsers();
    }

    @Override
    public List<AdminUsers> getAllByName(String name) {

        return adminUsersMapper.getAllByName(name);
    }
}
