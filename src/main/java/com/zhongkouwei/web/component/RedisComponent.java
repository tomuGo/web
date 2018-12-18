package com.zhongkouwei.web.component;

import com.alibaba.fastjson.JSONObject;
import com.zhongkouwei.user.common.model.UserInfo;
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

    public Boolean exits(String key){
        return redisTemplate.hasKey(key);
    }

    public UserInfo getUserInfoFromRedis(String key){
        Object obj=redisTemplate.opsForValue().get(key);
        UserInfo userInfo=null;
        if(obj!=null){
            try {
                userInfo=JSONObject.parseObject(JSONObject.toJSONString(obj),UserInfo.class);
            }catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
        return userInfo;
    }

}
