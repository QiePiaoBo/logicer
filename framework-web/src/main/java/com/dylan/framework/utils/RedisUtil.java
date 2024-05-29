package com.dylan.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Classname RedisUtil
 * @Description RedisUtil
 * @Date 9/1/2023 4:48 PM
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> lgcRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void set(String key, Object value) {
        lgcRedisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return lgcRedisTemplate.opsForValue().get(key);
    }

    public void setList(String key, Object listValue){
        try {
            String str = objectMapper.writeValueAsString(listValue);
            set(key, str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getList(String key, Class<T> clazz){
        Object cacheObject = get(key);
        List<T> list = new ArrayList<>();
        if (cacheObject instanceof String) {
            String jsonString = (String) cacheObject;
            try {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
                TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {};
                list = objectMapper.readValue(jsonString, typeRef);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
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

