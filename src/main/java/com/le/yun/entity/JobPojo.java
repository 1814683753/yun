package com.le.yun.entity;

import lombok.Data;

import java.io.Serializable;
/**
 * @author ：hjl
 * @date ：2020/1/10 10:51
 * @description：
 * @modified By：
 */
@Data
public class JobPojo implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 5972926915663997551L;
    /**
     * job名字
     */
    private String jobName;
    /**
     * cron表达式
     */
    private String cronTime;
    /**
     * 任务描述
     */
    private String desc;
    /**
     * 具体执行的job的类
     */
    private String execMethod;
    /**
     * 状态
     */
    private String status;
}
