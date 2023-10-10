package com.cssl.playedu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cssl.playedu.domain.User;
import com.cssl.playedu.mapper.UserMapper;
import com.cssl.playedu.service.UserService;
import com.cssl.playedu.utils.HelperUtil;
import com.cssl.playedu.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> getUsers(String name, String email, String did, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.getUsers(name, email, did);
        return new PageInfo<>(users);
    }

    @Override
    public int addUser(String email, String name, String avatar, String password, String id_card) throws NoSuchAlgorithmException {
        String salt = HelperUtil.randomString(5);
        String pwd = StringUtil.toSha256(password + salt);
        System.out.println("pwd: " + pwd);
        return userMapper.addUser(email, name, avatar, pwd, id_card, salt);
    }

    @Override
    public int showAllUD(String email, String name) {
        return userMapper.showAllUD(email, name);
    }

    @Override
    public int addUserDepartment(Integer res, String dep_id) {
        return userMapper.addUserDepartment(res, dep_id);
    }

    @Override
    public List<User> showAll(String id) {
        return userMapper.showAll(id);
    }

    @Override
    public int updateUser(Integer id,String email, String name, String avatar, String password, String id_card) throws NoSuchAlgorithmException {
        String salt = HelperUtil.randomString(5);
        String pwd = StringUtil.toSha256(password + salt);
        System.out.println("pwd: " + pwd);
        return userMapper.updateUser(id,email, name, avatar, pwd, id_card, salt);
    }

    @Override
    public int updateUserDepartment(Integer res, String dep_id) {
        return userMapper.updateUserDepartment(res, dep_id);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public int deleteUserDepartment(Integer id) {
        return userMapper.deleteUserDepartment(id);
    }
}
