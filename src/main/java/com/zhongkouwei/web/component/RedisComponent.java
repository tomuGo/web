package com.zhongkouwei.web.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisComponent {

    @Value("${zhongkouwei.timeOut}")
    Long timeOut;

    @Autowired
    RedisTemplate redisTemplate;

    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value,timeOut,TimeUnit.DAYS);
    }

    public void del(List<String> keys){
        redisTemplate.delete(keys);
    }

}
