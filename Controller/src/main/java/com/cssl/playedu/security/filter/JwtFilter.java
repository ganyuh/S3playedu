package com.cssl.playedu.security.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.cssl.playedu.domain.AdminRole;
import com.cssl.playedu.domain.AdminUsers;
import com.cssl.playedu.utils.JwtUtil;
import com.cssl.playedu.utils.RedisUtil;
import com.cssl.playedu.utils.ResponseUtils;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author : Tang
 * @CreateDate 2023/8/29 17:24
 * <p>
 * 自定义一个Filter，用来拦截所有的请求判断是否携带Token
 * isAccessAllowed()判断是否携带了有效的JwtToken
 * onAccessDenied()是没有携带JwtToken的时候进行账号密码登录，登录成功允许访问，登录失败拒绝访问
 */
@Component
public class JwtFilter extends AccessControlFilter {

    @Value("${token.header}")
    private String tokenHeader = "Authorization";

    @Value("${token.prefix}")
    private String tokenPrefix = "Bearer ";
    /**
     * 签名 jwt 的密钥
     */
    @Value("${token.secret-key}")
    private String secretKey;

    private RedisUtil redisUtil;
    /**
     * 无需认证就可以访问的接口
     */
    private static final Set<String> PUBLIC_PATHS = new HashSet<>();

    static {
        PUBLIC_PATHS.add("/manage/admin/login");
    }


    /*
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpResponse.setHeader("Access-Control-Allow-Origin", httpResponse.getHeader("Origin"));
//            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            //允许请求方式 - POST, PUT, GET, OPTIONS, DELETE
//            httpResponse.setHeader("Access-Control-Allow-Methods", "*");
//            httpResponse.setHeader("Access-Control-Max-Age", "3600");
//            //需要放行header头部字段 如需鉴权字段，自行添加，如Authorization
//            httpResponse.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,token,Authorization,authorization");


//            httpResponse.setHeader("","");
//            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            try {
                String path = request.getRequestURI().substring(request.getContextPath().length());
                System.out.println("path：" + path);// com.cssl.controller.UserController#login(User)
                Result<Object> result = Result.err(ResultCode.TOKEN_PARSING_EXCEPTION, "请提供有效 token");
                if (PUBLIC_PATHS.contains(path)) {
                    return true;
                }
                String auth = request.getHeader(tokenHeader);
                if (!StringUtils.hasLength(auth)) {
                    ResponseUtils.printWriter(response, result);
                }
                String token = auth.replace(tokenPrefix, "").trim();
                System.out.println("token: " + token);
                if (StrUtil.isEmpty(token)) {
                    ResponseUtils.printWriter(response, result);
                }
                Map<String, Claim> claimMap = JwtUtil.verifyToken(token, secretKey);
//                request.getSession().setAttribute("adminUser", claimMap);
                System.out.println("claimMap: " + claimMap);
                AdminUsers adminUsers = new AdminUsers();
                adminUsers.setId(claimMap.get("uid").asInt());
                adminUsers.setEmail(claimMap.get("email").asString());
                adminUsers.setName(claimMap.get("name").asString());
                adminUsers.setRoles(JSON.parseArray(claimMap.get("roles").asString(), AdminRole.class));
                request.getSession().setAttribute("adminUser", adminUsers);
//                redisUtil.set("adminUser", adminUsers, 60 * 60 * 24 * 7);
                return true;
            } catch (Exception e) {
                ResponseUtils.printWriter(response, Result.err(ResultCode.TOKEN_PARSING_EXCEPTION, "token解析异常"));
            }
        }
        return false;
    }
}
