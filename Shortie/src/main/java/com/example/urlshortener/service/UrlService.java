package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    private final String BASE = "http://short.ly/";
    private final String CHAR_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int SHORT_LENGTH = 6;

    public String shortenUrl(String originalUrl, String customAlias) {
        String shortUrl = (customAlias != null && !customAlias.isEmpty()) ? customAlias : generateShortCode();

        Url url = new Url();
        url.setShortUrl(shortUrl);
        url.setOriginalUrl(originalUrl);
        url.setClickCount(0);

        repository.save(url);
        return BASE + shortUrl;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        Optional<Url> urlOpt = repository.findById(shortCode);
        urlOpt.ifPresent(url -> {
            url.setClickCount(url.getClickCount() + 1);
            repository.save(url);
        });
        return urlOpt.map(Url::getOriginalUrl);
    }

    private String generateShortCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_LENGTH; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return sb.toString();
    }
}