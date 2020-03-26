package com.le.yun.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hjl
 * @date ：2019/12/3 16:52
 * @description： 请求通用类
 * @modified By：
 */
@Data
public class QueryRequestPojo implements Serializable {

    private static final long serialVersionUID = -3452418317297639533L;
    /**
     * 页号
     */
    private Integer currentPage;
    /**
     * 每页数据大小
     */
    private Integer pageSize;
    /**
     * 查询条件
     */
    private String condition;
    /**
     * 启用排序的列
     */
    private String sortColumn;
    /**
     * 启用排序方式
     */
    private String sortMethod;
}
