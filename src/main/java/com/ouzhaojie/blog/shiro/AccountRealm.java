package com.ouzhaojie.blog.shiro;

import com.ouzhaojie.blog.entity.User;
import com.ouzhaojie.blog.service.UserService;
import com.ouzhaojie.blog.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // 注入到realm里面
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JwtToken; // 支持的是jwttoken而不是其他token

    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) authenticationToken;

        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(userId);

        if(user == null){
            throw new UnknownAccountException("账户不存在或密码错误");
        }

        if(user.getStatus() == -1){
            throw new LockedAccountException("账户已经被锁定");
        }

        // 加密用户敏感信息
        AccountProfile profile = new AccountProfile();
        BeanUtils.copyProperties(user, profile);// 用户信息复制到profile

        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
