package com.example.url_shortner.controller;

import com.example.url_shortner.dto.ShortenResponse;
import com.example.url_shortner.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {
    private final UrlService urlService;
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@RequestBody String url) {
        String key = urlService.shortenUrl(url);
        return ResponseEntity.ok(new ShortenResponse("http://localhost:8080/" + key));
    }
    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
        String originalUrl = urlService.getOriginalUrl(shortKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
