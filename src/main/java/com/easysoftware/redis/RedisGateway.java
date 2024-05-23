package com.easysoftware.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    /**
     * Delete a key from Redis.
     *
     * @param key The key to delete.
     * @return True if the key was deleted successfully, false otherwise.
     */
    public boolean deleteKey(final String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }


    /**
     * Scan keys in Redis based on a namespace.
     *
     * @param namespace The namespace to scan keys for.
     * @return A list of keys that match the namespace.
     */
    public List<String> scanKey(final String namespace) {

        String query = wildCard(namespace);
        List<String> listKeys = new ArrayList<>();
        ScanOptions options = ScanOptions.scanOptions().match(query).build();

        try (Cursor<String> curosr = stringRedisTemplate.scan(options)) {
            while (curosr.hasNext()) {
                String key = curosr.next();
                listKeys.add(key);
            }
        }

        return listKeys;
    }

    /**
     * Apply wildcard to a string.
     *
     * @param str The string to apply the wildcard to.
     * @return The string with wildcard applied.
     */
    private String wildCard(final String str) {
        return String.format(Locale.ROOT, "%s*", str);
    }
}
