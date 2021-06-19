package com.example.redis.common.controller;

import com.example.redis.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisPopController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("redisPop")
    public void redisPop(){
        for (int i = 0; i<=10; i++){
            Object o = redisUtil.leftPop("PRIZE-COUNT");
            System.out.println(Thread.currentThread() + ":" + o);
        }

    }


}
