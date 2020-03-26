package com.le.yun.service;

import com.le.yun.entity.Role;
import com.le.yun.entity.User;

import java.util.List;

/**
 * @author ：hjl
 * @date ：2019/11/24 16:45
 * @description：角色管理相关的接口
 * @modified By：
 */
public interface RoleManageService {
    /**
     * 获取指定用户下所拥有的角色
     * @param user
     * @return
     */
    List<Role> queryRoleByUser(User user);
}
