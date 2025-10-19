# Shortie — URL Shortener

Shortie is a simple URL shortening service implemented in Java using Spring Boot. It generates short aliases for long URLs and redirects users from the short link to the original URL.

Key features
- Short URLs created using UUIDs + Base62 encoding for strong uniqueness.
- REST endpoints to shorten URLs and resolve short codes.
- Persistence using JPA (configured for PostgreSQL in pom.xml).
- OpenAPI/Swagger UI (springdoc) for interactive API docs.

Quick start
1. Build the project:

```cmd
cd g:\abhi\portfolio-projects\Shortie
mvn -DskipTests package
```

2. Run the application:

```cmd
mvn spring-boot:run
```

3. Open Swagger UI in your browser (default):

http://localhost:8080/swagger-ui.html

API endpoints
- POST /shortie/shorten — Accepts JSON { "originalUrl": "https://...", "customAlias": "optional" } and returns the shortened URL.
- GET /shortie/{shortCode} — Returns the original URL for the given short code.

How short codes are generated
- The service now uses java.util.UUID.randomUUID() to produce a 128-bit value, converts it to bytes, encodes those bytes into Base62 (0-9a-zA-Z), and returns the first 7 characters of that Base62 string. This approach provides strong uniqueness and produces compact short codes.

Documenting with Swagger / OpenAPI
- The project includes the `springdoc-openapi-starter-webmvc-ui` dependency which automatically exposes OpenAPI metadata and a Swagger UI.
- To document a controller or method, you can use annotations provided by `io.swagger.v3.oas.annotations`.
  - Annotate controllers with `@Tag(name = "...")`.
  - Annotate endpoints with `@Operation(summary = "...", description = "...")`.
  - Use `@Parameter` for parameters and `@ApiResponse` to document responses.

Example (controller method):

```java
@Tag(name = "URL Shortener", description = "API for shortening URLs")
@RestController
@RequestMapping("/shortie")
public class UrlController {

    @Operation(summary = "Shorten a URL", description = "Generates a short URL for the given original URL.")
    @PostMapping("/shorten")
    public String shorten(@RequestBody Map<String,String> request) { ... }
}
```

Notes on code quality warnings
- The IDE may show warnings like "field is never assigned" for Spring `@Autowired` fields; these are injected at runtime by Spring and can be safely ignored or replaced with constructor injection to satisfy static analysis.

Next steps / Improvements
- Add constructor injection for services and repositories to remove static-analysis warnings.
- Add unit and integration tests for the `UrlService` and controller.
- Add rate limiting and analytics for production readiness.

License
MIT

