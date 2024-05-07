
package com.easysoftware.redis;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    /**
     * RedisGateway instance for interacting with Redis.
     */
    @Resource
    private RedisGateway redisGateway;

    /**
     * Set a key-value pair in Redis.
     *
     * @param key   The key to set.
     * @param value The value to set.
     * @return ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<Object> setKey(final String key, final String value) {
        redisGateway.set(key, value);

        Map<String, Object> res = Map.ofEntries(
                Map.entry("key", key),
                Map.entry("msg", "Successfully set the key")
        );
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Get the value associated with a key in Redis.
     *
     * @param key The key to retrieve the value for.
     * @return ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<Object> getKey(final String key) {
        String value = redisGateway.get(key);

        Map<String, Object> res = Map.ofEntries(
                Map.entry("key", key),
                Map.entry("value", value)
        );

        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Set a key-value pair in Redis with an expiration time.
     *
     * @param key     The key to set.
     * @param value   The value to set.
     * @param timeout The expiration time.
     * @param unit    The time unit for the expiration time.
     * @return ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<Object> setKeyWithExpire(final String key, final String value,
                                                   final long timeout, final TimeUnit unit) {
        redisGateway.setWithExpire(key, value, timeout, unit);

        Map<String, Object> res = Map.ofEntries(
                Map.entry("key", key),
                Map.entry("timeout", timeout),
                Map.entry("msg", "Successfully set the key with expiration")
        );
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Check if a key exists in Redis.
     *
     * @param key The key to check.
     * @return ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<Object> hasKey(final String key) {
        boolean keyExsit = redisGateway.hasKey(key);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("key", key),
                Map.entry("keyExsit", keyExsit)
        );

        return ResultUtil.success(HttpStatus.OK, res);
    }


    /**
     * Delete a key from Redis.
     *
     * @param key The key to delete.
     * @return ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<Object> deleteKey(final String key) {
        boolean keyExsit = redisGateway.hasKey(key);
        // key检查 不存在key直接返回
        if (!keyExsit) {
            Map<String, Object> res = Map.ofEntries(
                    Map.entry("key", key),
                    Map.entry("msg", MessageCode.EC00016.getMsgZh())
            );
            return ResultUtil.success(HttpStatus.OK, res);
        }

        // key存在时执行删除
        boolean keyDelte = redisGateway.delteKey(key);
        if (!keyDelte) {
            Map<String, Object> res = Map.ofEntries(
                    Map.entry("key", key),
                    Map.entry("msg", MessageCode.EC00017.getMsgZh())
            );
            return ResultUtil.success(HttpStatus.OK, res);
        }

        String msg = String.format("成功删除key %s", key);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    /**
     * Scan keys in Redis based on a namespace.
     *
     * @param namespace The namespace to scan keys for.
     * @return ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<Object> scanKeyByNameSpace(final String namespace) {

        List<String> resKeys = redisGateway.scanKey(namespace);

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", resKeys.size()),
                Map.entry("keys", resKeys)
        );

        return ResultUtil.success(HttpStatus.OK, res);
    }


    /**
     * Update Redis based on a namespace.
     *
     * @param namespace The namespace to update in Redis.
     * @return ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<Object> updateRedisByNameSapce(final String namespace){
        List<String> resKeys = redisGateway.scanKey(namespace);

        Map<String, String> resMap = new HashMap<>();

        for (String key : resKeys) {
            // key 检查 key不存在直接返回
            boolean keyExsit = redisGateway.hasKey(key);
            if (!keyExsit) {
                resMap.put(key, "key does't exsit12");
                continue;
            }

            boolean isSuccess = redisGateway.delteKey(key);
            if (!isSuccess) {
                resMap.put(key, "key delete failed");
            } else {
                resMap.put(key, "key delete success");
            }
        }

        return ResultUtil.success(HttpStatus.OK, resMap);
    }
}
