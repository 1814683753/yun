package com.le.yun;

import com.le.yun.util.ThreadPoolUtil;
import com.mchange.v2.lang.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

/**
 * @author ：hjl
 * @date ：2020/1/17 14:35
 * @description：
 * @modified By：
 */
@SpringBootTest
@Slf4j
public class ThreadTest {

    @Test
    void test() throws Exception {
        ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPoolExecutor(1, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        Callable callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "TEST";
            }
        };
        Future<String> future = executor.submit(callable);
        String result = future.get();
        executor.shutdown();
        System.out.println("result = " + result);
    }


}
