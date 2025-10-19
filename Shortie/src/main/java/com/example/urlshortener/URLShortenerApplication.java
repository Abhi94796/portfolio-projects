package com.example.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class URLShortenerApplication {

    private static final Logger log = LoggerFactory.getLogger(URLShortenerApplication.class);

    public static void main(String[] args) {
        log.info("Starting Shortie (URLShortenerApplication) with args={}", (Object) args);

        SpringApplication.run(URLShortenerApplication.class, args);
        
        log.info("Shortie application started !!");
    }
}