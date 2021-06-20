package com.example.redis.common.controller;

import com.example.redis.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisPopController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("redisPop")
    public synchronized void redisPop(){
        List<Object> objects = redisUtil.leftPopList("PRIZE-COUNT");
        System.out.println(Thread.currentThread()+""+objects);
    }


}
