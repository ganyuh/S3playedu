package com.cssl.playedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cssl.playedu.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    /**
     * 查询所有学员
     */
    List<User> getUsers(String name,String email,String did);

    /**
     * 添加学员信息gyh版本
     * @return
     */
    int addUser(String email,String name,String avatar,String password,String id_card, String salt);

    int showAllUD(String email,String name);

    int addUserDepartment(Integer res,String dep_id);

    List<User> showAll(String id);

    /**
     * 修改学员信息
     * @return
     */
    int updateUser(Integer id,String email,String name,String avatar,String password,String id_card, String salt);

    int updateUserDepartment(Integer res,String dep_id);

    /**
     * 删除学员信息
     */
    int deleteUser(Integer id);

    int deleteUserDepartment(Integer id);
}
