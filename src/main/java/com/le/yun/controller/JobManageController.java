package com.le.yun.controller;

import com.le.yun.entity.JobPojo;
import com.le.yun.entity.ResponseMessage;
import com.le.yun.job.TestJob;
import com.le.yun.util.JobUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ：hjl
 * @date ：2020/1/10 10:22
 * @description： 任务管理相关controller
 * @modified By：
 */
@RestController
@Slf4j
@RequestMapping("/job/")
public class JobManageController {

    @RequestMapping("createJob")
    @ResponseBody
    public ResponseMessage createJob(@RequestBody JobPojo pojo){
        ThreadPoolExecutor executor = JobUtils.executor;
        if (null==executor){
            return ResponseMessage.FAILED;
        }
        executor.execute(new TestJob(pojo));
        return ResponseMessage.SUCCESS;
    }
    @RequestMapping("stopJob")
    @ResponseBody
    public ResponseMessage stopJob(@RequestBody JobPojo pojo){
        boolean flag = JobUtils.stopJob(pojo.getJobName());
        if (flag){
            return ResponseMessage.SUCCESS;
        }
        return ResponseMessage.FAILED;
    }
    @RequestMapping("upload")
    public void upload(CommonsMultipartFile file){
        System.out.println(file.getName());
    }


}
