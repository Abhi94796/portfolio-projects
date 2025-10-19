package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for URL shortening and retrieval.
 * Contains business logic for generating short codes and handling URL mapping.
 */
@Service
public class UrlService {

    private static final Logger log = LoggerFactory.getLogger(UrlService.class);

    private final UrlRepository repository;

    private final String BASE = "http://short.ly/";

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String shortenUrl(String originalUrl, String customAlias) {
        log.info("shortenUrl called with originalUrl='{}' customAlias='{}'", originalUrl, customAlias);
        String shortUrl = (customAlias != null && !customAlias.isEmpty()) ? customAlias : generateShortCode(originalUrl);

        Url url = new Url();
        url.setShortUrl(shortUrl);
        url.setOriginalUrl(originalUrl);
        url.setClickCount(0);

        repository.save(url);
        log.info("Saved short URL mapping: {} -> {}", shortUrl, originalUrl);
        return BASE + shortUrl;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        log.info("getOriginalUrl called for shortCode='{}'", shortCode);
        Optional<Url> urlOpt = repository.findById(shortCode);
        urlOpt.ifPresent(url -> {
            url.setClickCount(url.getClickCount() + 1);
            repository.save(url);
            log.info("Incremented click count for shortCode='{}', newCount={}", shortCode, url.getClickCount());
        });
        if (urlOpt.isPresent()) {
            log.info("Found original URL for shortCode='{}' -> '{}'", shortCode, urlOpt.get().getOriginalUrl());
        } else {
            log.warn("No mapping found for shortCode='{}'", shortCode);
        }
        return urlOpt.map(Url::getOriginalUrl);
    }

    /*  -Generates a short code using a UUID and encodes it in base62.
        -UUID provides strong uniqueness properties.
        -Returns a 7-character code.
    * */
    private String generateShortCode(String originalUrl) {
        log.debug("generateShortCode called for originalUrl='{}'", originalUrl);
        // Generate until we find a code that doesn't already exist (extremely unlikely collision)
        String code;
        int attempts = 0;
        do {
            UUID uuid = UUID.randomUUID();
            ByteBuffer bb = ByteBuffer.allocate(16);
            bb.putLong(uuid.getMostSignificantBits());
            bb.putLong(uuid.getLeastSignificantBits());
            byte[] uuidBytes = bb.array();
            String base62 = toBase62(uuidBytes);
            code = base62.substring(0, Math.min(7, base62.length()));
            attempts++;
            log.debug("Attempt {} generated code='{}'", attempts, code);
            if (repository.existsById(code)) {
                log.warn("Collision detected for code='{}' on attempt {} - retrying", code, attempts);
            }
        } while (repository.existsById(code) && attempts < 20);
        if (attempts > 1) {
            log.info("generateShortCode required {} attempts to find unique code='{}'", attempts, code);
        }
        return code;
    }

    private String toBase62(byte[] bytes) {
        final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        BigInteger bi = new BigInteger(1, bytes);
        StringBuilder sb = new StringBuilder();
        while (bi.compareTo(BigInteger.ZERO) > 0) {
            int idx = bi.mod(BigInteger.valueOf(62)).intValue();
            sb.append(BASE62.charAt(idx));
            bi = bi.divide(BigInteger.valueOf(62));
        }
        // Ensure at least one character
        if (sb.length() == 0) {
            return String.valueOf(BASE62.charAt(0));
        }
        return sb.reverse().toString();
    }
}