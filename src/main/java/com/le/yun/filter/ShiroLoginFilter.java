package com.le.yun.filter;

import com.alibaba.fastjson.JSONObject;
import com.le.yun.constant.Constants;
import com.le.yun.entity.ResponseMessage;
import com.le.yun.util.WebUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：hjl
 * @date ：2019/12/22 21:31
 * @description：
 * @modified By：
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {
    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (WebUtils.isAjax(request)) {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            ResponseMessage resultData = new ResponseMessage();
            resultData.setCode(Constants.AUTH_ERROR_CODE);
            resultData.setMessage("登录认证失效，请重新登录!");
            resultData.setResult("failed");
            httpServletResponse.getWriter().write(JSONObject.toJSON(resultData).toString());
        } else {
            /**
             * @Mark 非ajax请求重定向为登录页面
             */
            httpServletResponse.sendRedirect("/login");
        }
        return false;
    }


}
