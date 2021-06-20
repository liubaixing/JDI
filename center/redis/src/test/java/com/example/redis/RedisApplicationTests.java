package com.example.redis;

import com.example.redis.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    String key = "PRIZE-COUNT";

    @Test
    public void test() {
        for (int i = 0; i < 100; i++){
            redisUtil.rightPush(key,i);
        }
    }

    @Test
    public void getList(){
        List<Object> objects = redisUtil.leftPopList(key);
        System.out.println(objects);
    }



}
