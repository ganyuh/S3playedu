package com.cssl.playedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cssl.playedu.domain.User;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService extends IService<User> {
    PageInfo<User> getUsers(String name,String email,String did, Integer pageNum,Integer pageSize);

    /**
     * 添加学员信息gyh版本
     * @return
     */
    int addUser(String email,String name,String avatar,String password,String id_card) throws NoSuchAlgorithmException;

    int showAllUD(String email,String name);

    int addUserDepartment(Integer res,String dep_id);

    List<User> showAll(String id);

    /**
     * 修改学员信息
     * @return
     */
    int updateUser(Integer id,String email,String name,String avatar,String password,String id_card) throws NoSuchAlgorithmException;

    int updateUserDepartment(Integer res,String dep_id);


    /**
     * 删除学员信息
     */
    int deleteUser(Integer id);

    int deleteUserDepartment(Integer id);
}
