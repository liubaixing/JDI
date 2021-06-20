package com.example.redis.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /**
     * 设置过期时间
     * @param key key
     * @param time 过期时间（秒）
     * @return true/false
     */
    public boolean expire(String key,long time){
        try{
            if (time > 0) {
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key,Object value,long time){
        try{
            redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    //List 操作

    /**
     * 指定 list 从左入栈
     * @param key
     * @return 当前队列的长度
     */
    public Long leftPush(String key,Object value){
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 指定 list 从左出栈
     * 如果列表没有元素,会堵塞到列表一直有元素或者超时为止
     * @param key
     * @return 出栈的值
     */
    public Object leftPop(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    public List<Object> leftPopList(String key){
        return redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                listRange(key,0,2);
                listTrim(key,3,-1);
                return null;
            }
        });
    }

    /**
     * 从左边依次入栈
     * 导入顺序按照 Collection 顺序
     * 如: a b c => c b a
     * @param key
     * @param values
     * @return
     */
    public Long leftPushAll(String key, List<Object> values){
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 指定 list 从右入栈
     * @param key
     * @return 当前队列的长度
     */
    public Long rightPush(String key,Object value){
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 指定 list 从右出栈
     * 如果列表没有元素,会堵塞到列表一直有元素或者超时为止
     * @param key
     * @return 出栈的值
     */
    public Object rightPop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }


    /**
     * 从右边依次入栈
     * 导入顺序按照 Collection 顺序
     * 如: a b c => a b c
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(String key, List<Object> values){
        return redisTemplate.opsForList().rightPushAll(key, values);
    }


    /**
     * 根据下标获取值
     * @param key
     * @param index
     * @return
     */
    public Object index(String key,long index){
        return redisTemplate.opsForList().index(key, index);
    }


    /**
     * 获取列表指定长度
     * @param key
     * @return
     */
    public Long listSize(String key){
        return redisTemplate.opsForList().size(key);
    }


    /**
     * 获取列表 指定范围内的所有值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> listRange(String key,long start,long end){
        return redisTemplate.opsForList().range(key, start, end);
    }


    /**
     * 删除 key 中 值为 value 的 count 个数.
     * @param key
     * @param count
     * @param value
     * @return 成功删除的个数
     */
    public Long listRemove(String key,long count,Object value){
        return redisTemplate.opsForList().remove(key, count, value);
    }


    /**
     * 删除 列表 [start,end] 以外的所有元素
     * @param key
     * @param start
     * @param end
     */
    public void listTrim(String key,long start,long end){
        redisTemplate.opsForList().trim(key, start, end);

    }

    /**
     * 将 key 右出栈,并左入栈到 key2
     *
     * @param key 右出栈的列表
     * @param key2 左入栈的列表
     * @return 操作的值
     */
    public Object rightPopAndLeftPush(String key,String key2){
        return redisTemplate.opsForList().rightPopAndLeftPush(key, key2);

    }

}
