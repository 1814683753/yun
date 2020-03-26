package com.le.yun.filter;

import com.alibaba.fastjson.JSONObject;
import com.le.yun.constant.Constants;
import com.le.yun.entity.ResponseMessage;
import com.le.yun.util.WebUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：hjl
 * @date ：2019/12/26 16:23
 * @description：自定义登出过滤器
 * @modified By：
 */
public class MyLogoutFilter extends LogoutFilter {

    private static final Logger LOG = LoggerFactory.getLogger(MyLogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        try {
            if (WebUtils.isAjax(request)){
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                Subject subject = this.getSubject(request, response);
                subject.logout();
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json");
                ResponseMessage resultData = new ResponseMessage();
                resultData.setCode(Constants.SUCCESS_CODE);
                resultData.setMessage("退出成功");
                resultData.setResult(Constants.SUCCESS);
                httpServletResponse.getWriter().write(JSONObject.toJSON(resultData).toString());
            }else{
                super.preHandle(request,response);
            }
        } catch (SessionException var6) {
            LOG.error("Encountered session exception during logout.  This can generally safely be ignored.", var6);
        }
       return false;
    }
}
