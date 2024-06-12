/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.redis;

import org.springframework.http.ResponseEntity;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    /**
     * Get the value associated with a key in Redis.
     *
     * @param key The key to retrieve the value for.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> getKey(String key);

    /**
     * Set a key-value pair in Redis with an expiration time.
     *
     * @param key     The key to set.
     * @param value   The value to set.
     * @param timeout The expiration time.
     * @param unit    The time unit for the expiration time.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> setKeyWithExpire(String key, String value, long timeout, TimeUnit unit);

    /**
     * Check if a key exists in Redis.
     *
     * @param key The key to check.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> hasKey(String key);
}
