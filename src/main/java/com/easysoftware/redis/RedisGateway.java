package com.easysoftware.redis;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisGateway {
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

    public boolean delteKey(String key) {  
        return stringRedisTemplate.delete(key);  
    }  

    public List<String> scanKey(String namespace) {  
        
        String query = wildCard(namespace);
        List<String> listKeys = new ArrayList<>();;
        ScanOptions options = ScanOptions.scanOptions().match(query).build();
        Cursor<String> curosr = stringRedisTemplate.scan(options);
        
        while(curosr.hasNext()){
            String key  = curosr.next();
            listKeys.add(key);
        }
        // 关闭cursor
        curosr.close();

        return listKeys;
    }
    
    String wildCard(String str){
        return String.format("%s*", str);
    }
}
