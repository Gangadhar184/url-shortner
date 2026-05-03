package com.example.url_shortner.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "url_mapping")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortKey;

    @Column(nullable = false, length = 2000)
    private String originalUrl;

    private Instant createdAt;

    private Instant expiresAt;

    public UrlMapping() {}
    public Long getId() {
        return id;
    }
    public String getShortKey() {
        return shortKey;
    }
    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }
    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getExpiresAt() {
        return  expiresAt;
    }
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
