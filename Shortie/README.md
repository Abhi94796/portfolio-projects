# Shortie - URL Shortener

Shortie is a simple URL shortening service built with Spring Boot. It allows users to shorten long URLs, optionally specify custom aliases, and redirect to the original URLs using the generated short codes.

## Features
- Shorten any valid URL to a short code
- Optionally provide a custom alias for your short URL
- Redirect to the original URL using the short code
- Simple REST API endpoints

## Technologies Used
- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 (in-memory) or any SQL database

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven

### Running the Application
1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd Shortie
   ```
2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
3. The application will start on `http://localhost:8080` by default.

### API Endpoints

#### 1. Shorten a URL
- **Endpoint:** `POST /shortie/shorten`
- **Request Body:**
  ```json
  {
    "originalUrl": "https://example.com/very/long/url",
    "customAlias": "myalias" // optional
  }
  ```
- **Response:**
  - Returns the shortened URL or alias.

#### 2. Redirect to Original URL
- **Endpoint:** `GET /shortie/{shortCode}`
- **Response:**
  - Returns the original URL if found, or an error message if not found.

### Example Usage

**Shorten a URL:**
```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"originalUrl": "https://www.google.com"}' \
  http://localhost:8080/shortie/shorten
```

**Redirect (get original URL):**
```bash
curl http://localhost:8080/shortie/abc123
```

### Configuration
- Edit `src/main/resources/application.properties` to configure database or server settings as needed.

## Project Structure
```
Shortie/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/example/urlshortener/
        │       ├── controller/
        │       ├── model/
        │       ├── repository/
        │       └── service/
        └── resources/
            └── application.properties
```


