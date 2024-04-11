package com.easysoftware.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;



public interface RedisService {
    ResponseEntity<Object> setKey(String key, String value);
    ResponseEntity<Object> getKey(String key);
    ResponseEntity<Object> setKeyWithExpire(String key, String value, long timeout, TimeUnit unit);
    ResponseEntity<Object> hasKey(String key);
    ResponseEntity<Object> deleteKey(String key);
    ResponseEntity<Object> scanKeyByNameSpace(String namespace);
}
