package com.easysoftware.redis;

import org.springframework.http.ResponseEntity;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    /**
     * Set a key-value pair in Redis.
     *
     * @param key   The key to set.
     * @param value The value to set.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> setKey(String key, String value);

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

    /**
     * Scan keys in Redis based on a namespace.
     *
     * @param namespace The namespace to scan keys for.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> scanKeyByNameSpace(String namespace);

    /**
     * Update Redis based on a namespace.
     *
     * @param namespace The namespace to update in Redis.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> updateRedisByNameSapce(String namespace);

}
