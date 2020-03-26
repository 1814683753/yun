package com.le.yun.config;

import com.alibaba.fastjson.JSON;
import com.le.yun.entity.Permission;
import com.le.yun.entity.Role;
import com.le.yun.entity.User;
import com.le.yun.service.UserManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

import java.util.List;

/**
 * @author ：hjl
 * @date ：2019/11/24 16:37
 * @description：
 * @modified By：
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserManageService userManageService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("=====权限认证=====");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        log.info("**********"+user.toString());
        try {
            User roles = userManageService.getUserByName(user.getName());
            List<Role> roleList = roles.getRoleList();
            if (CollectionUtils.isNotEmpty(roleList)){
                for (Role role:roleList){
                    authorizationInfo.addRole(role.getRoleName());
                    List<Permission> permissionList = role.getPermissionList();
                    if (CollectionUtils.isNotEmpty(permissionList)){
                        for (Permission permission : permissionList){
                            authorizationInfo.addStringPermission(permission.getPermissionName());
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("doGetAuthorizationInfo error: ",e);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户账号和密码
        log.info("用户认证");
        String username = (String) authenticationToken.getPrincipal();
        User user = userManageService.getUserByName(username);
        log.info("userName : {} , user : {}",username, JSON.toJSONString(user));
        if(user == null){
            return null;
        }
        //用数据库中的盐值作为盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                getName()
        );
        authenticationInfo.setCredentialsSalt(credentialsSalt);
        return authenticationInfo;
    }
}
