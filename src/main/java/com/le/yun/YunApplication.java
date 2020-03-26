package com.le.yun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author ：hjl
 * @date ：2019/11/21 13:45
 * @description： 启动类
 * @modified By：
 */
@SpringBootApplication
@MapperScan("com.le.yun.mapper")
@EnableCaching
public class YunApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunApplication.class, args);
    }

}
