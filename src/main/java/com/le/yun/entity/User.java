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
 * @date ：2019/11/21 13:39
 * @description： 用户
 * @modified By：
 */
@Data
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = -8783490657884751608L;
    /**
     * 主键，用户id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户姓名
     */
    @TableField(value = "name")
    private String name;
    /**
     * 用户年龄
     */
    @TableField(value = "age")
    private Integer age;
    /**
     * 邮件
     */
    @TableField(value = "email")
    private String email;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 加盐的值
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 图片地址
     */
    @TableField(value = "pictureAddress")
    private String pictureAddress;
    /**
     * 用户下所有角色
     */
    @TableField(exist = false)
    private List<Role> roleList;
}
