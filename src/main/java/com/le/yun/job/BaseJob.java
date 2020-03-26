package com.le.yun.job;
import com.le.yun.entity.JobPojo;
import com.le.yun.util.JobUtils;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author ：hjl
 * @date ：2020/1/10 14:55
 * @description： 基础任务线程，子类重写exec即可
 * @modified By：
 */
@Slf4j
public class BaseJob implements Runnable{

    /**
     * 任务参数
     */
    private JobPojo pojo;

    protected BaseJob(JobPojo pojo) {
        this.pojo = pojo;
    }



    /**
     * 记录执行的次数
     */
    private int count;

    /**
     * 具体执行的内容，子类重写该方法即可
     */
    protected void exec(){
    }

    @Override
    public void run(){
        // 是否线程中断的标识，当线程被中断时设置成false
        boolean flag = true;
        while(true){
            // 获取所有执行的时间点,每次只获取当天的，循环获取
            List<Date> dates = JobUtils.getExecTime(pojo.getCronTime());
            Iterator<Date> iterator = dates.iterator();
            while (iterator.hasNext()){
                // 获取执行的时间点
                Date date = iterator.next();
                count = 0;
                while (!Thread.interrupted()){
                    Thread.currentThread().setName(pojo.getJobName());
                    Date currentTime = new Date();
                    // 到达执行时间时执行
                    if (date.getTime()==currentTime.getTime() || currentTime.after(date)){
                        exec();
                        flag = false;
                        count++;
                        break;
                    }
                }
                // 强制终止线程时退出循环
                if (flag){
                    break;
                }
                // 正常执行完成后，标识置为true
                flag = true;
            }
            // 强制终止线程时退出循环
            if (flag&&count<dates.size()){
                break;
            }
        }
        log.info("{} end exec ...........",pojo.getJobName());
    }
}
