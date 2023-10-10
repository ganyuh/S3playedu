package com.cssl.playedu.config;

import com.cssl.playedu.security.auth.EmailAuthenticationRealm;
import com.cssl.playedu.security.filter.JwtFilter;
import com.cssl.playedu.security.filter.ShiroLoginFilter;
import com.cssl.playedu.security.filter.ShiroPermissionFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public Realm eamilAuthRealm(@Value("${my-shiro-config.hash-algorithm}") String hashAlgorithm, @Value("${my-shiro-config.hash-iterations}") Integer hashIter) {
        EmailAuthenticationRealm realm = new EmailAuthenticationRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(hashAlgorithm);
        if (hashIter != null) {
            matcher.setHashIterations(hashIter);
        }
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(List<Realm> realms) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关闭 subjectDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultWebSessionStorageEvaluator storageEvaluator = new DefaultWebSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        storageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(storageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 设置realm
        securityManager.setRealms(realms);
        // 添加认证策略 - 至少一项成功的策略
        ModularRealmAuthenticator authenticator = (ModularRealmAuthenticator) securityManager.getAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, JwtFilter jwtFilter) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = shiroFilter.getFilters();
        filters.put("perms", new ShiroPermissionFilter());
        filters.put("authc", new ShiroLoginFilter());
        filters.put("jwt", jwtFilter);
//        filters.put("anon", new AnonymousFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterRuleMap = new HashMap<>();
        filterRuleMap.put("/manage/admin/login", "anon");
//        filterRuleMap.put("/**", "authc");
//        filterRuleMap.put("/**", "anon");
        filterRuleMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilter;
    }
}
