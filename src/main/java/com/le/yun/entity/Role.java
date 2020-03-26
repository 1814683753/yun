package com.le.yun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：hjl
 * @date ：2019/11/21 14:44
 * @description： 角色
 * @modified By：
 */
@Data
@TableName("t_role")
public class Role implements Serializable {


    private static final long serialVersionUID = 1220933663181104152L;
    /**
     * 角色主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;
    /**
     * 角色所拥有的
     */
    @TableField(exist = false)
    private List<Permission> permissionList;
}
