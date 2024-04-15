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
    // key更新操作，传入namespace更新namespace下所有页面缓存
    @GetMapping("/update")
    public ResponseEntity<Object> updateRedisByNameSapce(@RequestParam String namespace) {
        ResponseEntity<Object> res = redisService.updateRedisByNameSapce(namespace);
        return res;
    }
}
