package com.example.url_shortner.service;

import com.example.url_shortner.model.UrlMapping;
import com.example.url_shortner.repository.UrlRepository;
import com.example.url_shortner.util.Base62Encoder;
import com.example.url_shortner.util.SnowflakeIdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.Instant;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final SnowflakeIdGenerator idGenerator;
    public UrlService(UrlRepository urlRepository, SnowflakeIdGenerator idGenerator) {
        this.urlRepository = urlRepository;
        this.idGenerator = idGenerator;
    }
    @Transactional
    public String shortenUrl(String originalUrl) {
        validateUrl(originalUrl);
//        UrlMapping model = new UrlMapping();
//        model.setOriginalUrl(originalUrl);
//        model.setCreatedAt(Instant.now());
//        model = urlRepository.save(model);
//        String shortKey = Base62Encoder.encode(model.getId());
//        model.setShortKey(shortKey);
//        urlRepository.save(model);
//        return shortKey;
        long id = idGenerator.nextId();//no db call
        String shortKey = Base62Encoder.encode(id);
        UrlMapping model = new  UrlMapping();
        model.setShortKey(shortKey);
        model.setOriginalUrl(originalUrl);
        model.setCreatedAt(Instant.now());
        urlRepository.save(model); //now single write
        return shortKey;
    }
    public String getOriginalUrl(String shortKey) {
        return urlRepository
                .findValidOriginalUrl(shortKey)
                .orElseThrow(() -> new RuntimeException("URL not found or expired"));
    }

    private void validateUrl(String url) {
        try {
            URI uri = new URI(url);
            if (!("http".equalsIgnoreCase(uri.getScheme()) ||
                    "https".equalsIgnoreCase(uri.getScheme())
                    )) {
                throw new RuntimeException("Invalid URL");
            }
        }catch (Exception e){
            throw new RuntimeException("Invalid URL");
        }
    }
}
