package com.configandauth.config.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SessionCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void store(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(15));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}