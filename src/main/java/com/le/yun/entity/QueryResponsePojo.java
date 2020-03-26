package com.le.yun.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：hjl
 * @date ：2019/12/3 16:56
 * @description： 响应查询结果通用类
 * @modified By：
 */
@Data
public class QueryResponsePojo<T> implements Serializable {

    private static final long serialVersionUID = 8375443827160644549L;
    /**
     * 所有记录
     */
    private List<T> rows;
    /**
     * 总数
     */
    private Long total;
}
