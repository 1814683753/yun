package com.le.yun.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hjl
 * @date ：2019/11/21 14:59
 * @description： 角色权限关联表
 * @modified By：
 */
@TableName("t_role_permission")
@Data
public class RolePermission implements Serializable {


    private static final long serialVersionUID = -7963254448485100084L;
    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Integer roleId;
    /**
     * 权限id
     */
    @TableField(value = "permission_id")
    private Integer permissionId;
}
