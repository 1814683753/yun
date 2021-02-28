package com.le.yun.config;

import com.alibaba.fastjson.JSON;
import com.le.yun.entity.Permission;
import com.le.yun.entity.User;
import com.le.yun.service.UserManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ：hjl
 * @date ：2019/11/24 16:37
 * @description：shiro realm
 * @modified By：
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserManageService userManageService;

    /**
     * 授权方法会执行多次
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("=====权限认证=====");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 取决于doGetAuthenticationInfo种传入的第一个参数值
        User user = (User) principalCollection.getPrimaryPrincipal();
        log.info("**********"+user.toString());
        if (CollectionUtils.isNotEmpty(user.getRoleList())){
            Set<String> roles = new HashSet<>();
            user.getRoleList().forEach(role -> roles.add(role.getRoleName()));
            authorizationInfo.setRoles(roles);
            List<String> permissionList = new ArrayList<>();
            user.getRoleList().forEach(role -> permissionList.addAll(role.getPermissionList().stream().map(Permission::getPermissionName).collect(Collectors.toList())));
            authorizationInfo.addStringPermissions(permissionList);
        }
        return authorizationInfo;
    }

    /**
     * 登录认证方法只会执行一次
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户账号和密码
        log.info("用户认证");
        String username = (String) authenticationToken.getPrincipal();
        // 再次将相关角色权限赋予到用户中，避免授权方法每次都需查库
        User user = userManageService.getUserByName(username);
        log.info("userName : {} , user : {}",username, JSON.toJSONString(user));
        if(user == null){
            return null;
        }
        //设置盐值,每个用户的可以不一样可以设置再数据库
        ByteSource credentialsSalt = ByteSource.Util.bytes(StringUtils.isNotBlank(user.getSalt()) ? user.getSalt() : "2");
        // subject.getPrincipal() 获取到的将是传入的第一个参数
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                getName()
        );
        authenticationInfo.setCredentialsSalt(credentialsSalt);
        return authenticationInfo;
    }
}
