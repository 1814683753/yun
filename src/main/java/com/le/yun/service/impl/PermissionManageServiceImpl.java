package com.le.yun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.le.yun.entity.Permission;
import com.le.yun.entity.Role;
import com.le.yun.entity.RolePermission;
import com.le.yun.mapper.PermissionMapper;
import com.le.yun.mapper.RolePermissionMapper;
import com.le.yun.service.PermissionManageService;
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
 * @date ：2019/11/24 16:49
 * @description：权限管理相关接口实现
 * @modified By：
 */
@Service
public class PermissionManageServiceImpl implements PermissionManageService {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionManageServiceImpl.class);
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    /**
     * 获取指定角色的所有权限
     *
     * @param role
     * @return
     */
    @Override
    public List<Permission> queryPermissionByRole(Role role) {
        List<Permission> permissionList = new ArrayList<>();
        try {
            LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(wrapper.eq(Objects.nonNull(role),RolePermission::getRoleId,role.getId()));
            if (CollectionUtils.isNotEmpty(rolePermissionList)){
                rolePermissionList.forEach(rolePermission -> {
                    Permission permission = permissionMapper.selectById(rolePermission.getPermissionId());
                    permissionList.add(permission);
                });
            }
        }catch (Exception e){
            LOG.error("queryPermissionByRole error : ",e);
        }
        return permissionList;
    }
}
