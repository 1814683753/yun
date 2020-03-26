package com.le.yun.job;

import com.le.yun.annotion.Job;
import com.le.yun.entity.JobPojo;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 * @author ：hjl
 * @date ：2020/1/12 17:45
 * @description：
 * @modified By：
 */
@Job(name = "testJob")
@Slf4j
public class TestJob extends BaseJob{

    public TestJob(JobPojo pojo) {
        super(pojo);
    }

    /**
     * 具体执行的内容
     */
    @Override
    protected void exec() {
        log.info("now : {}" , new Date());
    }
}
