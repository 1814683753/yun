package com.le.yun.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hjl
 * @date ：2019/11/21 14:02
 * @description： 用户角色关联
 * @modified By：
 */
@Data
@TableName("t_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -8055506235917888037L;
    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;
    /**
     * 角色id
     */
    @TableField("roleId")
    private Integer roleId;

}
