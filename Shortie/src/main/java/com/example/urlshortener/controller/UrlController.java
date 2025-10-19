package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "URL Shortener", description = "API for shortening URLs and retrieving original URLs")
@RestController
@RequestMapping("/shortie")
public class UrlController {

    private static final Logger log = LoggerFactory.getLogger(UrlController.class);

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
        log.info("UrlController initialized");
    }

    @Operation(summary = "Shorten a URL", description = "Generates a short URL for the given original URL. Optionally accepts a custom alias.")
    @PostMapping("/shorten")
    public String shorten(
            @Parameter(description = "Request body containing originalUrl and optional customAlias") @RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        String customAlias = request.get("customAlias");
        log.info("Received shorten request originalUrl='{}' customAlias='{}'", originalUrl, customAlias);
        try {
            String result = service.shortenUrl(originalUrl, customAlias);
            log.info("Shorten request succeeded for originalUrl='{}' -> '{}'", originalUrl, result);
            return result;
        } catch (Exception e) {
            log.error("Error while shortening URL '{}'" , originalUrl, e);
            throw e;
        }
    }

    @Operation(summary = "Get original URL", description = "Retrieves the original URL for a given short code.")
    @GetMapping("/{shortCode}")
    public String redirect(
            @Parameter(description = "Short code for the shortened URL") @PathVariable String shortCode) {
        log.info("Received redirect request for shortCode='{}'", shortCode);
        String response = service.getOriginalUrl(shortCode).orElse("URL not found");
        log.info("Redirect lookup for shortCode='{}' returned '{}'", shortCode, response);
        return response;
    }
}