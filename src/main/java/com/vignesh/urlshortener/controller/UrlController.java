package com.vignesh.urlshortener.controller;

import com.vignesh.urlshortener.dto.UrlRequest;
import com.vignesh.urlshortener.service.UrlService;
import com.vignesh.urlshortener.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService service;

    @Autowired
    private RateLimiterService rateLimiterService;

    @PostMapping("/shorten")
    public String shorten(@RequestBody UrlRequest request) {
        return service.createShortUrl(request);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(
            @PathVariable String code,
            HttpServletRequest request) {

        String ip = request.getRemoteAddr();

        // 🔥 RATE LIMIT CHECK
        if (!rateLimiterService.isAllowed(ip)) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS) // 429
                    .build();
        }

        String longUrl = service.getLongUrl(code);

        return ResponseEntity
                .status(HttpStatus.FOUND) // 302
                .location(URI.create(longUrl))
                .build();
    }
}
