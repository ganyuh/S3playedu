package com.cssl.playedu.security.filter;

import com.cssl.playedu.utils.ResponseUtils;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : Tang
 * @Create 2023/8/28 15:59
 */
public class ShiroLoginFilter extends UserFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        PrintWriter out = response.getWriter();
//        out.println(JSON.toJSONString(Result.err(ResultCode.NOT_LOGGED_IN, "未登录")));
//        out.flush();
//        out.close();
        ResponseUtils.printWriter((HttpServletResponse) response, Result.err(ResultCode.NOT_LOGGED_IN, "未登录"));
    }
}
