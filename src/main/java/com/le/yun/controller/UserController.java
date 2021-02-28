package com.le.yun.controller;

import com.alibaba.fastjson.JSON;
import com.le.yun.constant.Constants;
import com.le.yun.entity.QueryRequestPojo;
import com.le.yun.entity.QueryResponsePojo;
import com.le.yun.entity.ResponseMessage;
import com.le.yun.entity.User;
import com.le.yun.service.UserManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author ：hjl
 * @date ：2019/12/3 16:42
 * @description：
 * @modified By：
 */
@RestController
@RequestMapping("/user/")
@Slf4j
public class UserController {

    @Autowired
    private UserManageService userManageService;

    /**
     * 按条件查询所有用户
     * @param requestPojo
     * @return
     */
    @RequestMapping(value = "listAllUser")
    public QueryResponsePojo listAllUser(@RequestBody  QueryRequestPojo requestPojo){
        log.info("query param : {}", JSON.toJSONString(requestPojo));
        QueryResponsePojo result =  userManageService.getAllUser(requestPojo);
        log.info("result : {}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 获取当前用户名
     * @return
     */
    @RequestMapping(value = "getUserInfo")
    @ResponseBody
    public ResponseMessage getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        ResponseMessage<User> responseMessage = new ResponseMessage<>();
        responseMessage.setCode(Constants.SUCCESS_CODE);
        responseMessage.setMessage(Constants.SUCCESS);
        responseMessage.setResult(user);
        responseMessage.setSuccess(Boolean.TRUE);
        if (Objects.isNull(user)){
            responseMessage.setCode(Constants.ERROR_CODE);
            responseMessage.setMessage("用户未登录");
            responseMessage.setSuccess(Boolean.FALSE);
        }
        log.info("result : {}", JSON.toJSONString(responseMessage));
        return responseMessage;
    }
}
