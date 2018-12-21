package com.zhongkouwei.web.component;

import com.alibaba.fastjson.JSONObject;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.web.app.AppConstants;
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

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.DAYS);
    }

    public void set(UserInfo userInfo){
        redisTemplate.opsForValue().set(AppConstants.SESSION_USER+userInfo.getUserId(),userInfo);
    }

    public void del(List<String> keys) {
        redisTemplate.delete(keys);
    }

    public Boolean exits(String key) {
        return redisTemplate.hasKey(key);
    }

    public UserInfo getUserInfoFromRedis(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        return converUser(obj);
    }

    private UserInfo converUser(Object obj) {
        UserInfo userInfo = null;
        if (obj != null) {
            try {
                userInfo = JSONObject.parseObject(JSONObject.toJSONString(obj), UserInfo.class);
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
        return userInfo;
    }

    public UserInfo getUserInfoFromRedisByUserId(Integer userId) {
        Object obj = redisTemplate.opsForValue().get(AppConstants.SESSION_USER + userId);
        return converUser(obj);
    }

}
