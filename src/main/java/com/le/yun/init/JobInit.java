package com.le.yun.init;

import com.le.yun.annotion.Job;
import com.le.yun.util.JobUtils;
import com.le.yun.util.ThreadPoolUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author ：hjl
 * @date ：2020/1/10 12:52
 * @description：
 * @modified By：
 */
@Component
@Order(value = 1)
public class JobInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        JobUtils.executor = ThreadPoolUtil.getThreadPoolExecutor(5,20,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources("com/le/yun/job");
        while(enumeration.hasMoreElements()){
            URL url = enumeration.nextElement();
            URI uri = url.toURI();
            // 2. 通过File获得uri下的所有文件
            File file = new File(uri);
            File[] files = file.listFiles();
            for (File f : files) {
                String fName = f.getName();
                if (!fName.endsWith(".class")) {
                    continue;
                }
                fName = fName.substring(0, fName.length() - 6);
                String prefix = "com.le.yun.job.";
                String allName = prefix + fName;
                // 3. 通过反射加载类
                Class clazz = Class.forName(allName);
                Annotation[] annotations = clazz.getAnnotations();
                if (annotations!=null&&annotations.length>0){
                    for (Annotation annotation : annotations){
                        if (annotation instanceof Job){
                            System.out.println(((Job)annotation).name());
                        }

                    }
                }
            }
        }
    }
}
