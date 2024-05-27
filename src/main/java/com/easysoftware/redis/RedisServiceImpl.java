/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/


package com.easysoftware.redis;

import com.easysoftware.common.utils.ResultUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
                Map.entry("msg", "Successfully set the key"));
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
                Map.entry("value", value));

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
                Map.entry("msg", "Successfully set the key with expiration"));
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
                Map.entry("keyExsit", keyExsit));

        return ResultUtil.success(HttpStatus.OK, res);
    }
}
