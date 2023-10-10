package com.cssl.playedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cssl.playedu.domain.AdminUsers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminUsersMapper extends BaseMapper<AdminUsers> {

    /**
     * 根据邮箱查询管理员
     *
     * @param email 管理员邮箱
     * @return 返回从数据库中查询到管理员及角色
     */
    AdminUsers queryAdminByEmail(@Param("email") String email);

    /**
     * 查询AdminUsers中角色
     *
     * @return
     */
    List<AdminUsers> showAllAdminUsers();

    List<AdminUsers> getAllByName(String name);
}
