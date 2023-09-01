package com.dylan.framework.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Classname RedisUtil
 * @Description RedisUtil
 * @Date 9/1/2023 4:48 PM
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> lgcRedisTemplate;

    public void set(String key, Object value) {
        lgcRedisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return lgcRedisTemplate.opsForValue().get(key);
    }

    public void del(String key) {
        lgcRedisTemplate.delete(key);
    }

    public void lPush(String key, Object value) {
        lgcRedisTemplate.opsForList().leftPush(key, value);
    }

    public Object lPop(String key) {
        return lgcRedisTemplate.opsForList().leftPop(key);
    }

    public void sAdd(String key, Object value) {
        lgcRedisTemplate.opsForSet().add(key, value);
    }

    public Set<Object> sMembers(String key) {
        return lgcRedisTemplate.opsForSet().members(key);
    }

    public void zAdd(String key, Object value, double score) {
        lgcRedisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> zRange(String key, long start, long end) {
        return lgcRedisTemplate.opsForZSet().range(key, start, end);
    }

    public void hSet(String key, String field, Object value) {
        lgcRedisTemplate.opsForHash().put(key, field, value);
    }

    public Object hGet(String key, String field) {
        return lgcRedisTemplate.opsForHash().get(key, field);
    }
}

