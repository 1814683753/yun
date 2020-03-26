package com.le.yun;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class YunApplicationTests {

    @Test
    void getPassword() {
        /**
         * 根据密码，盐值，加密次数获取加密后的密码
         */
        String password = new Md5Hash("123","2",2).toString();
        log.info("password : {}",password);
    }

}
