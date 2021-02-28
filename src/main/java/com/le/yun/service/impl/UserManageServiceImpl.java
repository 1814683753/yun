package com.le.yun.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.yun.entity.QueryRequestPojo;
import com.le.yun.entity.QueryResponsePojo;
import com.le.yun.entity.Role;
import com.le.yun.entity.User;
import com.le.yun.mapper.UserMapper;
import com.le.yun.service.RoleManageService;
import com.le.yun.service.UserManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ：hjl
 * @date ：2019/11/24 17:10
 * @description：用户管理接口实现类
 * @modified By：
 */
@Service
@Slf4j
public class UserManageServiceImpl implements UserManageService {

    private static final Logger LOG = LoggerFactory.getLogger(UserManageServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleManageService roleManageService;
    /**
     * 根据用户名获取用户
     *
     * @param name
     * @return
     */
    @Override
    public User getUserByName(String name) {
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            List<User> userList = userMapper.selectList(queryWrapper.eq(StringUtils.isNotEmpty(name),User::getName,name));
            if (CollectionUtils.isNotEmpty(userList)){
                User user = userList.get(0);
                List<Role> roleList = roleManageService.queryRoleByUser(user);
                if (CollectionUtils.isNotEmpty(roleList)){
                    user.setRoleList(roleList);
                }
                return user;
            }
        }catch (Exception e){
            LOG.info("getUserByName error: ",e);
        }
        return null;
    }

    /**
     * 获取所有用户信息
     *
     * @return
     */
    @Override
    @Cacheable("users")
    public QueryResponsePojo getAllUser(QueryRequestPojo queryRequestPojo) {
        log.info("getAllUser request : {}", JSON.toJSONString(queryRequestPojo));
        QueryResponsePojo<User> responsePojo = new QueryResponsePojo<>();
        try {
            Page<User> page = new Page<>();
            if (null != queryRequestPojo.getCurrentPage()) {
                page.setCurrent(queryRequestPojo.getCurrentPage());
            }
            if (null != queryRequestPojo.getPageSize()) {
                page.setSize(queryRequestPojo.getPageSize());
            }
            IPage<User> userPage = userMapper.selectPage(page,null);
            Long total = userPage.getTotal();
            List<User> userList = userPage.getRecords();
            if (CollectionUtils.isNotEmpty(userList)){
                userList.forEach(user -> {
                    List<Role> roleList = roleManageService.queryRoleByUser(user);
                    if (CollectionUtils.isNotEmpty(roleList)){
                        user.setRoleList(roleList);
                    }
                });
            }
            responsePojo.setRows(userList);
            responsePojo.setTotal(total);
        }catch (Exception e){
            LOG.error("获取用户信息异常......",e);
        }
        log.info("getAllUser response : {}", JSON.toJSONString(responsePojo));
        return responsePojo;
    }
}
