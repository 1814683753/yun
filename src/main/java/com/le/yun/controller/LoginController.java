package com.le.yun.controller;

import com.alibaba.fastjson.JSON;
import com.le.yun.constant.Constants;
import com.le.yun.entity.ResponseMessage;
import com.le.yun.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：hjl
 * @date ：2019/11/26 16:01
 * @description：
 * @modified By：
 */
@Controller
@RequestMapping(value = "/common")
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    /**
     * 登录方法
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResponseMessage login(@RequestBody User userInfo) {
        LOG.info("request : {}",JSON.toJSONString(userInfo));
        ResponseMessage<String> responseMessage = new ResponseMessage<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getName(), userInfo.getPassword());
        try {
            subject.login(token);
            responseMessage.setCode(Constants.SUCCESS_CODE);
            responseMessage.setSuccess(Boolean.TRUE);
            responseMessage.setResult(subject.getSession().getId().toString());
        } catch (IncorrectCredentialsException e) {
            responseMessage.setCode(Constants.LOGIN_ERROR_CODE);
            responseMessage.setSuccess(Boolean.FALSE);
            responseMessage.setMessage("密码错误");
        } catch (LockedAccountException e) {
            responseMessage.setCode(Constants.LOGIN_ERROR_CODE);
            responseMessage.setSuccess(Boolean.FALSE);
            responseMessage.setMessage("登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            responseMessage.setCode(Constants.LOGIN_ERROR_CODE);
            responseMessage.setSuccess(Boolean.FALSE);
            responseMessage.setMessage("登录失败，该用户不存在");
        } catch (Exception e) {
            responseMessage.setCode(Constants.ERROR_CODE);
            responseMessage.setMessage("登录失败！" + e.getMessage());
            LOG.error("login error : ",e);
            responseMessage.setSuccess(Boolean.FALSE);
        }
        LOG.info("result : {}",JSON.toJSONString(responseMessage));
        return responseMessage;
    }


}
