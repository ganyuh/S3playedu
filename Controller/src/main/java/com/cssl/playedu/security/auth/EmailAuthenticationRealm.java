package com.cssl.playedu.security.auth;

import com.cssl.playedu.domain.AdminUsers;
import com.cssl.playedu.service.AdminUsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 邮箱登录的 Realm
 */
public class EmailAuthenticationRealm extends AuthorizingRealm {

    @Autowired
    private AdminUsersService usersService;

    /**
     * 进行授权
     *
     * @param principalCollection
     * @return 返回对应的权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 进行身份认证（登录认证）
     *
     * @param token 调用 Subject.login(token) 方法认证时传入的 token
     * @return 登录认证成功后，返回一个 AuthenticationInfo 实例
     * @throws AuthenticationException 认证失败时抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AdminUsers user = usersService.login(token.getPrincipal().toString());
        if (user == null) {
            throw new UnknownAccountException("账号或密码错误！");
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }
}
