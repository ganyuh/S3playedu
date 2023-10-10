package com.cssl.playedu.controller.backstage;

import com.cssl.playedu.domain.User;
import com.cssl.playedu.service.UserDepartmentService;
import com.cssl.playedu.service.UserService;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.UserData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/manage/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    /**
     * 查询所有学员
     */
    @GetMapping("/getUsers")
    public Result<PageInfo<User>> getUsers(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String did,
                                           @RequestParam(required = false, defaultValue = "1") Integer ps,
                                           @RequestParam(required = false, defaultValue = "10") Integer pn) {
        PageInfo<User> users = userService.getUsers(name, email, did, ps, pn);
        return Result.ok(users);
    }

    /**
     * 添加学员
     * @return
     */
    @PostMapping("/addUser")
    public Result<Object> addUser(@RequestBody UserData userData) throws NoSuchAlgorithmException {
        System.out.println(userData);
        int res = userService.addUser(userData.getEmail(), userData.getName(), userData.getAvatar(), userData.getPassword(), userData.getId_card());
        int user_id = userService.showAllUD(userData.getEmail(), userData.getName());
        int result = userService.addUserDepartment(user_id, userData.getDep_id());
        return Result.ok(res);
    }

    @GetMapping("/showAll")
    public Result<List<User>> showAll(@RequestParam(required = false) String id) {
        List<User> users = userService.showAll(id);
        return Result.ok(users);
    }

    @PutMapping("/updateUser")
    public Result<Object> updateUser(@RequestBody UserData userData) throws NoSuchAlgorithmException {
        System.out.println(userData);
        int res = userService.updateUser(userData.getId(),userData.getEmail(), userData.getName(), userData.getAvatar(), userData.getPassword(), userData.getId_card());
        int user_id = userService.showAllUD(userData.getEmail(), userData.getName());
        int result = userService.updateUserDepartment(user_id, userData.getDep_id());
        return Result.ok(res);
    }

    @DeleteMapping("deleteUser")
    public Result<Object> deleteUser(@RequestParam(required = false) Integer id) {
        return Result.ok(userService.deleteUser(id));
    }
}
