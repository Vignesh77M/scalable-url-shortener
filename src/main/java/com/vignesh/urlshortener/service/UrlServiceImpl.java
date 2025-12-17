
package com.vignesh.urlshortener.service;

import com.vignesh.urlshortener.dto.UrlRequest;
import com.vignesh.urlshortener.model.Url;
import com.vignesh.urlshortener.repository.UrlRepository;
import com.vignesh.urlshortener.util.ShortCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository repository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String createShortUrl(UrlRequest request) {
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresAt(request.getExpiresAt());
        url.setShortCode(ShortCodeGenerator.generate());
        repository.save(url);
        return url.getShortCode();
    }
    
    @Override
    public String getLongUrl(String shortCode) {

        // 1. Try Redis for fast lookup
        String longUrl = null;
        try {
            longUrl = redisTemplate.opsForValue().get(shortCode);
        } catch (Exception e) {
            // Redis down, ignore
        }

        // 2. ALWAYS load entity from DB for analytics
        Url url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        // 3. Increment click count
        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);

        // 4. Cache URL if not present
        if (longUrl == null) {
            try {
                redisTemplate.opsForValue().set(shortCode, url.getLongUrl());
            } catch (Exception e) {
                // ignore
            }
        }

        return url.getLongUrl();
    }


}
