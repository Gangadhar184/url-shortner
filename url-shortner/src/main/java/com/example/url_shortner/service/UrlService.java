package com.example.url_shortner.service;

import com.example.url_shortner.model.UrlMapping;
import com.example.url_shortner.repository.UrlRepository;
import com.example.url_shortner.util.Base62Encoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
    public String shortenUrl(String originalUrl) {
        UrlMapping model = new UrlMapping();
        model.setOriginalUrl(originalUrl);
        model.setCreatedAt(Instant.now());
        model = urlRepository.save(model);
        String shortKey = Base62Encoder.encode(model.getId());
        model.setShortKey(shortKey);
        urlRepository.save(model);
        return shortKey;
    }
    public String getOriginalUrl(String shortKey) {
        UrlMapping mapping = urlRepository.findByShortKey(shortKey)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        return mapping.getOriginalUrl();
    }
}
