package com.le.yun.service;

import com.le.yun.entity.QueryRequestPojo;
import com.le.yun.entity.QueryResponsePojo;
import com.le.yun.entity.User;

import java.util.List;


/**
 * @author ：hjl
 * @date ：2019/11/24 16:40
 * @description： 用户管理接口
 * @modified By：
 */
public interface UserManageService {
    /**
     * 根据用户名获取用户
     * @param name 用户名
     * @return 返回对应的用户
     */
    User getUserByName(String name);

    /**
     * 获取所有用户信息
     * @param queryRequestPojo 请求参数实例
     * @return 返回结果
     */
    QueryResponsePojo getAllUser(QueryRequestPojo queryRequestPojo);
}
