package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shortie")
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping("/shorten")
    public String shorten(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        String customAlias = request.get("customAlias");
        return service.shortenUrl(originalUrl, customAlias);
    }

    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode) {
        return service.getOriginalUrl(shortCode).orElse("URL not found");
    }
}