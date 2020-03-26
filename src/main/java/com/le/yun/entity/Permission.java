package com.le.yun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hjl
 * @date ：2019/11/21 14:56
 * @description： 权限表
 * @modified By：
 */
@TableName("t_permission")
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 9220427983436339027L;
    /**
     * 权限主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 权限名称
     */
    @TableField(value = "permissionName")
    private String permissionName;

}
