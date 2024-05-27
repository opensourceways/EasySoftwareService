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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RedisGateway {

    /**
     * Autowired StringRedisTemplate for Redis operations.
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Set a key-value pair in Redis.
     *
     * @param key   The key to set.
     * @param value The value to set.
     */
    public void set(final String key, final String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * Get the value associated with a key in Redis.
     *
     * @param key The key to retrieve the value for.
     * @return The value associated with the key.
     */
    public String get(final String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * Set a key-value pair in Redis with an expiration time.
     *
     * @param key     The key to set.
     * @param value   The value to set.
     * @param timeout The expiration time.
     * @param unit    The time unit for the expiration time.
     */
    public void setWithExpire(final String key, final String value, final long timeout, final TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Check if a key exists in Redis.
     *
     * @param key The key to check.
     * @return True if the key exists, false otherwise.
     */
    public boolean hasKey(final String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }
}
