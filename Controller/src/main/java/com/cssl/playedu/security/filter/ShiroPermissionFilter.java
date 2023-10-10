package com.cssl.playedu.security.filter;

import com.cssl.playedu.utils.ResponseUtils;
import com.cssl.playedu.vo.Result;
import com.cssl.playedu.vo.ResultCode;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : Tang
 * @CreateDate 2023/8/28 17:12
 */
public class ShiroPermissionFilter extends PermissionsAuthorizationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(200);
        Subject subject = this.getSubject(request, response);
        Result<Object> result = Result.err(ResultCode.NOT_LOGGED_IN, "您还没有登录，请登录！");
        if (subject.getPrincipal() != null) {
            result = Result.err(ResultCode.NO_ACCESS, "您还没有该权限，请联系管理员！");
        }
        ResponseUtils.printWriter((HttpServletResponse) response, result);
        return false;
    }
}
