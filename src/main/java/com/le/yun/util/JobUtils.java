package com.le.yun.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author ：hjl
 * @date ：2020/1/10 11:02
 * @description： job工具类
 * @modified By：
 */
@Slf4j
public class JobUtils {
    /**
     * 任务使用的线程池
     */
    public static ThreadPoolExecutor executor;

    /**
     * 根据cron表达式获取当天所有执行时间(获取全部太多了)
     * @param cron cron表达式
     * @return
     */
    public static List<Date> getExecTime(String cron){
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cron);
        } catch (ParseException e) {
            log.error("parse cron error :",e);
        }
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY,23);
        end.set(Calendar.MINUTE,59);
        end.set(Calendar.SECOND,59);
        end.set(Calendar.MILLISECOND,999);
        return TriggerUtils.computeFireTimesBetween(cronTriggerImpl,null,new Date(),end.getTime());
    }

    /**
     * 根据任务名停止任务
     * @param jobName 任务名
     * @return
     */
    public static boolean stopJob(String jobName){
        int count = Thread.activeCount();
        Thread[] ts = new Thread[count];
        // 将当前线程的线程组及其子组中的每一个活动线程复制到指定的数组中
        Thread.enumerate(ts);
        for (Thread t:ts) {
            if(Objects.nonNull(t)){
                if(jobName.equals(t.getName())){
                    // 中断指定线程
                    t.interrupt();
                    log.info("中断{} success",jobName);
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 判断任务是否在运行
     * @param jobName 任务名
     * @return 添加进线程池代码正在运行,否则代表未运行
     */
    public static boolean checkStatus(String jobName){
        int count = Thread.activeCount();
        Thread[] ts = new Thread[count];
        // 将当前线程的线程组及其子组中的每一个活动线程复制到指定的数组中
        Thread.enumerate(ts);
        for (Thread t:ts) {
            if(Objects.nonNull(t)){
                if(jobName.equals(t.getName())){
                    // 存在放回true
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }


}
