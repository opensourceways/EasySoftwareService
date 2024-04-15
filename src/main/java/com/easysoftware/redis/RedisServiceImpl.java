
package com.easysoftware.redis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.annotation.Resource;

import java.util.Map;
import java.util.concurrent.TimeUnit;  
  
@Service  
public class RedisServiceImpl implements RedisService{  
  
    @Resource  
    private RedisGateway redisGateway;   
    
    @Override
    public ResponseEntity<Object> setKey(String key, String value) { 
        redisGateway.set(key, value);

        Map res = Map.ofEntries(
            Map.entry("key", key),
            Map.entry("msg", "Successfully set the key")
        );
        return ResultUtil.success(HttpStatus.OK, res); 
    }  
    
    @Override
    public ResponseEntity<Object> getKey(String key) {  
        String value = redisGateway.get(key);

        Map res = Map.ofEntries(
            Map.entry("key", key),
            Map.entry("value", value)
        );

        return ResultUtil.success(HttpStatus.OK, res);
    }  
    
    @Override
    public ResponseEntity<Object> setKeyWithExpire(String key, String value, long timeout, TimeUnit unit) {  
        redisGateway.setWithExpire(key, value, timeout, unit);

        Map res = Map.ofEntries(
            Map.entry("key", key),
            Map.entry("timeout", timeout),
            Map.entry("msg", "Successfully set the key with expiration")
        );
        return ResultUtil.success(HttpStatus.OK, res);
    }  
    
    @Override
    public ResponseEntity<Object> hasKey(String key) {  

        boolean keyExsit = redisGateway.hasKey(key);
        Map res = Map.ofEntries(
            Map.entry("key", key),
            Map.entry("keyExsit", keyExsit)
        );

        return ResultUtil.success(HttpStatus.OK, res);
    }  


    @Override
    public ResponseEntity<Object> deleteKey(String key) {  
        boolean keyExsit = redisGateway.hasKey(key);
        // key检查 不存在key直接返回 
        if(!keyExsit){
            Map res = Map.ofEntries(
                Map.entry("key", key),
                Map.entry("msg", MessageCode.EC00016.getMsgZh())
            );
            return ResultUtil.success(HttpStatus.OK, res);
        }

        // key存在时执行删除
        boolean keyDelte = redisGateway.delteKey(key);
        if(!keyDelte){
            Map res = Map.ofEntries(
                Map.entry("key", key),
                Map.entry("msg", MessageCode.EC00017.getMsgZh())
            );
            return ResultUtil.success(HttpStatus.OK, res);
        }
        
        String msg = String.format("成功删除key %s",key);
        return ResultUtil.success(HttpStatus.OK, msg);
    }  

    @Override
    public ResponseEntity<Object> scanKeyByNameSpace(String namespace) {  
    
        List<String> resKeys = redisGateway.scanKey(namespace);

        Map res = Map.ofEntries(
            Map.entry("total", resKeys.size()),
            Map.entry("keys", resKeys)
        );

        return ResultUtil.success(HttpStatus.OK, res);
    }


    @Override
    public ResponseEntity<Object> updateRedisByNameSapce(String namespace) {  
        List<String> resKeys = redisGateway.scanKey(namespace);

        Map<String,String> resMap = new HashMap<>();
        
        for(String key : resKeys){
            // key 检查 key不存在直接返回
            boolean keyExsit = redisGateway.hasKey(key);
            if(!keyExsit){
                resMap.put(key, "key does't exsit");
                continue;
            }

            boolean isSuccess = redisGateway.delteKey(key);
            if(isSuccess == false){
                resMap.put(key, "key delete failed");
            }else{
                resMap.put(key, "key delete success");
            }
        }

        return ResultUtil.success(HttpStatus.OK, resMap);
    }
}