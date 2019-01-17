package com.security.demo.shiro;

import com.security.demo.domain.entity.UserAccountInfo;
import com.security.demo.domain.entity.UserRoleInfo;
import com.security.demo.domain.repository.UserAccountInfoRepository;
import com.security.demo.domain.repository.UserRoleInfoRepository;
import com.security.demo.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserAccountInfoRepository userAccountInfoRepository;

    @Autowired
    UserRoleInfoRepository userRoleInfoRepository;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserAccountInfo userAccountInfo = (UserAccountInfo)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        return simpleAuthorizationInfo;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比

        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }

        UserAccountInfo userAccountInfo = userAccountInfoRepository.findByDisplayName(username);
        if (ObjectUtils.isEmpty(userAccountInfo)) {
            throw new AuthenticationException("用户不存在!");
        }

        if (!JwtUtil.verify(token, username, userAccountInfo.getSecurityPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }

        return new SimpleAuthenticationInfo(userAccountInfo, token, "my_realm");

    }
}
