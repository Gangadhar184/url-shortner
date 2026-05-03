package com.example.url_shortner.controller;

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
    public String shorten(@RequestBody String url) {
        String shortKey = urlService.shortenUrl(url);
        return "http://localhost:8080/" + shortKey;
    }
    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
        String originalUrl = urlService.getOriginalUrl(shortKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
