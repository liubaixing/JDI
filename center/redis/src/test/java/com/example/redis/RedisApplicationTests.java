package com.example.redis;

import com.example.redis.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    String key = "PRIZE-COUNT";

    @Test
    public void test() {
        for (int i = 0; i < 10; i++){
            redisUtil.rightPush(key,i);
        }
    }

    @Test
    public void getList(){
        for (int i = 0; i < 5; i++){
            Object o = redisUtil.leftPop(key);
            System.out.println(Thread.currentThread() + " : " +o);
        }
    }

    @Test
    public void redisPop(){

        new Thread(()->{
            for (int i = 0; i < 5; i++){
                Object o = redisUtil.leftPop(key);
                System.out.println(Thread.currentThread() + " : " +o);
            }
        }).start();

//        new Thread(()->{
//            for (int i = 0; i < 5; i++){
//                Object o = redisUtil.leftPop(key);
//                System.out.println(Thread.currentThread() + " : " +o);
//            }
//        }).start();

//        new Thread(()->{
//            for (int i = 0; i < 5; i++){
//                Object o = redisUtil.leftPop(key);
//                System.out.println(Thread.currentThread() + " : " +o);
//            }
//        }).start();

    }

}
