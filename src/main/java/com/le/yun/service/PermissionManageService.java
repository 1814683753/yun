package com.le.yun.service;

import com.le.yun.entity.Permission;
import com.le.yun.entity.Role;

import java.util.List;

/**
 * @author ：hjl
 * @date ：2019/11/24 16:47
 * @description：权限管理相关的接口
 * @modified By：
 */
public interface PermissionManageService {
    /**
     * 获取指定角色的所有权限
     * @param role
     * @return
     */
    List<Permission> queryPermissionByRole(Role role);
}
