package com.le.yun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.le.yun.entity.Permission;
import com.le.yun.entity.Role;
import com.le.yun.entity.User;
import com.le.yun.entity.UserRole;
import com.le.yun.mapper.RoleMapper;
import com.le.yun.mapper.UserRoleMapper;
import com.le.yun.service.PermissionManageService;
import com.le.yun.service.RoleManageService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ：hjl
 * @date ：2019/11/24 17:01
 * @description：角色管理相关的接口实现类
 * @modified By：
 */
@Service
public class RoleManageServiceImpl implements RoleManageService {
    private static final Logger LOG = LoggerFactory.getLogger(RoleManageServiceImpl.class);
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermissionManageService permissionManageService;
    /**
     * 获取指定用户下所拥有的角色
     *
     * @param user
     * @return
     */
    @Override
    public List<Role> queryRoleByUser(User user) {
        List<Role> roleList = new ArrayList<>();
        try {
            LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
            List<UserRole> userRoleList = userRoleMapper.selectList(queryWrapper.eq(Objects.nonNull(user),UserRole::getUserId,user.getId()));
            if (CollectionUtils.isNotEmpty(userRoleList)){
                userRoleList.forEach(userRole -> {
                    Role role = roleMapper.selectById(userRole.getRoleId());
                    if (Objects.nonNull(role)){
                        List<Permission> permissionList = permissionManageService.queryPermissionByRole(role);
                        if (CollectionUtils.isNotEmpty(permissionList)){
                            role.setPermissionList(permissionList);
                        }
                        roleList.add(role);
                    }
                });
            }
        }catch (Exception e){
            LOG.info("queryRoleByUser error : ",e);
        }
        return roleList;
    }
}
