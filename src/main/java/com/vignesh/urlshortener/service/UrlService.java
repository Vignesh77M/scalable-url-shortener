
package com.vignesh.urlshortener.service;

import com.vignesh.urlshortener.dto.UrlRequest;

public interface UrlService {
    String createShortUrl(UrlRequest request);
    String getLongUrl(String shortCode);
}
