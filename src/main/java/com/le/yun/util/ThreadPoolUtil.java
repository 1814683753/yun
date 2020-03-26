package com.le.yun.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：hjl
 * @date ：2020/1/17 13:31
 * @description： 线程工具类
 * @modified By：
 */
public class ThreadPoolUtil {

    /**
     * 获取线程池
     * @param corePoolSize 核心线程数，默认情况下核心线程会一直存活，即使处于闲置状态也不会受存keepAliveTime限制
     * @param maximumPoolSize 线程池所能容纳的最大线程数。超过这个数的线程将被阻塞。当任务队列为没有设置大小的LinkedBlockingDeque时，这个值无效。
     * @param keepAliveTime 非核心线程的闲置超时时间，超过这个时间就会被回收。
     * @param unit 指定keepAliveTime的单位，如TimeUnit.SECONDS。当将allowCoreThreadTimeOut设置为true时对corePoolSize生效。
     * @param workQueue 线程池中的任务队列.常用的有三种队列，SynchronousQueue,LinkedBlockingDeque,ArrayBlockingQueue
     * @return 线程池
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue){
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, unit,workQueue,getThreadFactory());
    }

    /**
     * 创建线程工厂,对线程池中的线程进行一系列处理
     * @return
     */
    private static ThreadFactory getThreadFactory(){
        AtomicInteger tag = new AtomicInteger(1);
        return (Runnable r) ->{
            Thread thread = new Thread(r);
            thread.setName("线程-demo-" + tag.getAndIncrement());
            return thread;
        };
    }
}
