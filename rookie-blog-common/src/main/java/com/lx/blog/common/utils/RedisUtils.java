package com.lx.blog.common.utils;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 李旭
 * @date 2025/11/14
 * @description Redis 工具类
 */
@Component
@RequiredArgsConstructor
public class RedisUtils {

    @NotNull
    private final RedisTemplate<String, Object> redisTemplate;
    @NotNull
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 设置键值对，支持过期时间
     * @param key 键
     * @param value 值
     * @param ttl 过期时间，null 表示不过期
     */
    public void set(String key, Object value, Duration ttl) {
        if (ttl == null || ttl.isZero() || ttl.isNegative()) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, ttl);
        }
    }

    /**
     * 获取键对应的值
     * @param key 键
     * @param type 值的类型
     * @return 值，未找到返回 null
     */
    public <T> T get(String key, Class<T> type) {
        Object val = redisTemplate.opsForValue().get(key);
        if (val == null) return null;
        return type.cast(val);
    }

    /**
     * 设置哈希字段的值
     * @param key 键
     * @param field 字段
     * @param value 值
     */
    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取哈希字段的值
     * @param key 键
     * @param field 字段
     * @param type 值的类型
     * @return 值，未找到返回 null
     */
    public <T> T hGet(String key, String field, Class<T> type) {
        Object val = redisTemplate.opsForHash().get(key, field);
        if (val == null) return null;
        return type.cast(val);
    }

    /**
     * 左推送到列表
     * @param key 键
     * @param values 值
     */
    public void lPush(String key, Object... values) {
        for (Object v : values) {
            redisTemplate.opsForList().leftPush(key, v);
        }
    }

    /**
     * 获取列表指定范围内的元素
     * @param key 键
     * @param start 开始索引
     * @param end 结束索引
     * @return 元素列表
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 检查键是否存在
     * @param key 键
     * @return true 存在，false 不存在
     */
    public boolean exists(String key) {
        Boolean r = redisTemplate.hasKey(key);
        return r != null && r;
    }

    /**
     * 删除指定键
     * @param keys 键列表
     * @return 删除成功的键数量
     */
    public long del(String... keys) {
        long c = 0;
        for (String k : keys) {
            Boolean r = redisTemplate.delete(k);
            if (r != null && r) c++;
        }
        return c;
    }

    /**
     * 对字符串值进行递增操作
     * @param key 键
     * @return 递增后的值
     */
    public Long incr(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    /**
     * 对字符串值进行递减操作
     * @param key 键
     * @return 递减后的值
     */
    public Long decr(String key) {
        return stringRedisTemplate.opsForValue().decrement(key);
    }

    /**
     * 设置键的过期时间
     * @param key 键
     * @param ttl 过期时间，null 表示不过期
     */
    public void expire(String key, Duration ttl) {
        if (ttl != null && !ttl.isNegative() && !ttl.isZero()) {
            redisTemplate.expire(key, ttl.toMillis(), TimeUnit.MILLISECONDS);
        }
    }
}