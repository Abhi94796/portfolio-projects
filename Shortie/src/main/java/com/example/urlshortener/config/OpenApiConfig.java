package com.example.urlshortener.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final Logger log = LoggerFactory.getLogger(OpenApiConfig.class);

    @Bean
    public OpenAPI customOpenAPI() {
        log.info("Creating OpenAPI bean for Shortie API");
        return new OpenAPI()
                .info(new Info()
                        .title("Shortie API")
                        .version("1.0.0")
                        .description("A simple URL shortener API")
                        .contact(new Contact()
                                .name("Shortie Team")
                                .email("dev@example.com")
                        )
                );
    }
}
