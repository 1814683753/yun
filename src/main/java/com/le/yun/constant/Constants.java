package com.le.yun.constant;

/**
 * @author ：hjl
 * @date ：2019/11/21 15:23
 * @description： 常量类
 * @modified By：
 */
public interface Constants {
    /**
     * 降序
     */
    String DESC = "desc";
    /**
     * 升序
     */
    String ASC = "asc";
    /**
     * ajax的请求头值
     */
    String XML_HTTP_REQUEST = "XMLHttpRequest";
    /**
     * 正常成功编码
     */
    Integer SUCCESS_CODE = 200;
    /**
     * 登录异常编码
     */
    Integer LOGIN_ERROR_CODE = -1;
    /**
     * 认证失败
     */
    Integer AUTH_ERROR_CODE = 403;
    /**
     * 普通的错误编码
     */
    Integer ERROR_CODE = 1;
    /**
     * 成功信息
     */
    String SUCCESS = "success";
    /**
     * 失败信息
     */
    String FAILED = "failed";
}
