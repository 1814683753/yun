package com.le.yun.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author ：hjl
 * @date ：2019/11/24 17:54
 * @description：自定义SessionManager
 * @modified By：
 */
@Slf4j
public class MySessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        String token = httpServletRequest.getHeader("Authorization");
        log.info("Authorization：{}",token);
        if(!StringUtils.isEmpty(token)){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "Stateless request");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        }else {
            return super.getSessionId(request, response);
        }

    }
}
