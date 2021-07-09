package com.yinchaxian.bookshop.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public PermissionRealm permissionRealm() {
        PermissionRealm permissionRealm = new PermissionRealm();
        permissionRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return permissionRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(permissionRealm());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/login", "anon");
        map.put("/register", "anon");
        map.put("/shop/**", "anon");
        map.put("/**", "authc");

        bean.setFilterChainDefinitionMap(map);
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/login");
        return bean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密方式 采取 md5 方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密次数 加密2次
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
}
