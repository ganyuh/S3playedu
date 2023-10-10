package com.cssl.playedu.controller.backstage;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cssl.playedu.domain.AdminUsers;
import com.cssl.playedu.dto.UserLoginDto;
import com.cssl.playedu.service.AdminUsersService;
import com.cssl.playedu.utils.JwtUtil;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理用户
 */

@RestController
@RequestMapping("/manage/admin")
public class AdminUserController {

    @Autowired
    private AdminUsersService usersService;

    /**
     * jwt 的有效时间
     */
    @Value("${token.expireTime}")
    private Integer expireTime = 24 * 60 * 60 * 1000;

    /**
     * 签名 jwt 的密钥
     */
    @Value("${token.secret-key}")
    private String secretKey;

    /**
     * 后台用户登录
     */
    @PostMapping("/login")
    public Result<JSONObject> login(@Validated @RequestBody UserLoginDto userLogin) {
        System.out.println("userLogin = " + userLogin);
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(userLogin.getEmail(), userLogin.getPassword()));
        } catch (AuthenticationException e) {
            return Result.err(ResultCode.REQUEST_FAILED, "用户名或密码错误");
        }
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            AdminUsers user = (AdminUsers) currentUser.getPrincipal();
            // 使用 principal 进行相关操作
            System.out.println("已认证的主体信息(user): " + user);
            Map<String, Object> admin = new HashMap<>();
            admin.put("uid", user.getId());
            admin.put("email", user.getEmail());
            admin.put("name", user.getName());
            admin.put("roles", JSON.toJSONString(user.getRoles()));
            System.out.println("expireTime: " + expireTime);
            String token = JwtUtil.createToken(admin, secretKey, expireTime);
            JSONObject tokens = new JSONObject();
            tokens.set("token", token);
            System.out.println("token: " + JwtUtil.verifyToken(token, secretKey));
            return Result.ok(tokens).setMessage("登录成功");
        } else {
            // 用户未认证
            System.out.println("用户未登录或认证失败");
            return Result.err(ResultCode.REQUEST_FAILED, "用户名或密码错误");
        }
    }

    /**
     * 获取当前登录信息 -
     */
    @GetMapping("/info")
    public Result<Object> getInfo(@SessionAttribute("adminUser") Map<String, Claim> adminUser) {
        System.out.println("SessionAttribute adminUser: " + adminUser);
        return Result.ok(usersService.getOne(new LambdaQueryWrapper<AdminUsers>().eq(AdminUsers::getId, adminUser.get("uid").asInt())));
    }

    /**
     * 查询admin_users表中数据
     */
    @Autowired
    private AdminUsersService adminUsersService;
    @GetMapping("/showAllAdminUsers")
    public Result<List<AdminUsers>> showAllAdminUsers(){

        List<AdminUsers> aus=adminUsersService.showAllAdminUsers();

        return Result.ok(aus);
    }

    @GetMapping("/getAllByName")
    public Result<List<AdminUsers>> getAllByName(String name){

        List<AdminUsers> aus=adminUsersService.getAllByName(name);

        return Result.ok(aus);
    }
}
