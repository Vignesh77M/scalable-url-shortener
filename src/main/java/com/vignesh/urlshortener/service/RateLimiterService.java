package com.vignesh.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 10;
    private static final int WINDOW_SECONDS = 60;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isAllowed(String ip) {
        String key = "rate:" + ip;
        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, WINDOW_SECONDS, TimeUnit.SECONDS);
        }

        return count != null && count <= MAX_REQUESTS;
    }
}
