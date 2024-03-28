
package com.easysoftware.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;  
import org.springframework.stereotype.Service;  
  
import java.util.concurrent.TimeUnit;  
  
@Service  
public class RedisService {  
  
    @Autowired  
    private StringRedisTemplate stringRedisTemplate;  
    

    public void set(String key, String value) {  
        stringRedisTemplate.opsForValue().set(key, value);  
    }  
  
    public String get(String key) {  
        return stringRedisTemplate.opsForValue().get(key);  
    }  
  
    public void setWithExpire(String key, String value, long timeout, TimeUnit unit) {  
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);  
    }  
  
    public boolean hasKey(String key) {  
        return stringRedisTemplate.hasKey(key);  
    }  
}