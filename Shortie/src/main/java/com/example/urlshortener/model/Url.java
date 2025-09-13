package com.example.urlshortener.model;

import jakarta.persistence.*;

@Entity
public class Url {
    @Id
    private String shortUrl;
    private String originalUrl;
    private long clickCount;

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public long getClickCount() { return clickCount; }
    public void setClickCount(long clickCount) { this.clickCount = clickCount; }
}