package com.easysoftware.adapter.execute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.easysoftware.redis.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/redis")
public class RedisExecuteAdapter {

    /**
     * Autowired service for interacting with Redis.
     */
    @Autowired
    private RedisService redisService;

    /**
     * Endpoint to update Redis cache by namespace, updating all pages under the specified namespace.
     *
     * @param namespace The namespace for which cache needs to be updated.
     * @return  ResponseEntity<Object>.
     */
    // key更新操作，传入namespace更新namespace下所有页面缓存
    @GetMapping("/update")
    public ResponseEntity<Object> updateRedisByNameSapce(@RequestParam final String namespace) {
        return redisService.updateRedisByNameSapce(namespace);
    }
}
