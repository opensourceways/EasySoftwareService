package com.easysoftware.adapter.execute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.easysoftware.redis.RedisService;
import com.easysoftware.redis.RedisServiceImpl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/redis")
public class RedisExecuteAdapter {
    @Autowired
    private RedisService redisService;   

    // 缓存淘汰接口，动态页面 config刷新时调用该接口淘汰缓存
    @DeleteMapping(value = "/{key}")
    public ResponseEntity<Object> deleteKey(@PathVariable String key) {
        ResponseEntity<Object> res = redisService.deleteKey(key);
        return res;
    }

    // 获取所有动态页面相关config key，该接口返回的所有key，config刷新后均需删除
    @GetMapping("/config")
    public ResponseEntity<Object> getConfigKey(@RequestParam String namespace) {
        ResponseEntity<Object> res = redisService.scanKeyByNameSpace(namespace);
        return res;
    }
}
