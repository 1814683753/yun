package com.le.yun.util;

import com.le.yun.constant.Constants;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：hjl
 * @date ：2019/12/26 17:04
 * @description： web相关工具类
 * @modified By：
 */
public class WebUtils {

    /**
     * 判断是否是ajax
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if(Constants.XML_HTTP_REQUEST.equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
