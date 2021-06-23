package com.example.redis;

import com.example.redis.common.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    @Test
    public void distinct(){
        List<User> list = Arrays.asList(
                new User(1,"zhangsan")
                ,new User(2,"zhangsan")
                ,new User(3,"zhangsan")
                ,new User(5,"李四")
                ,new User(4,"李四")
                ,new User(6,"zhangsan")
                ,new User(7,"zhangsan")
                ,new User(8,"zhangsan")
                ,new User(9,"李四")
        );

        List<Member> members = list.stream().sorted(Comparator.comparing(User::getId).reversed())
                .map(user -> new Member(user.getId(), user.getName())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Member::getName))), ArrayList::new));
        System.out.println(members);
    }

    @AllArgsConstructor
    @Data
    class User{
        private Integer id;
        private String name;
    }

    @AllArgsConstructor
    @Data
    class Member{
        private Integer memberId;
        private String name;
    }

    private static final Integer MAX_SEND = 50;

    @Test
    public void proell(){
        List<Integer> source = new ArrayList<>(1000000);
        for (int i = 1; i<= 1000000; i++){
            source.add(i);
        }

        int count = (source.size() + MAX_SEND - 1) / MAX_SEND;
        Set<String> set = new TreeSet<String>();
        List<List<Integer>> target = new ArrayList<>();
        long s = System.currentTimeMillis();
        Stream.iterate(0, i -> i + 1).limit(count).parallel().forEach(a -> {
            List<Integer> weimoStorageMos = source.stream().skip((long) a * MAX_SEND).limit(MAX_SEND).parallel().collect(Collectors.toList());
            target.add(weimoStorageMos);
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            set.add(Thread.currentThread().getName());
        });
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - s));
        System.out.println(target.size());

        System.out.println(set);

    }

}
