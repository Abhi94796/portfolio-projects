# Talksy - Real-Time Chat Application

## Overview
Talksy is a WhatsApp-like real-time chat application built with Java, Spring Boot, WebSockets, Kafka (pub-sub), and Redis. It demonstrates scalable, distributed, and persistent messaging using modern backend technologies.

## Features
- Real-time messaging using WebSockets
- Distributed message delivery with Kafka (pub-sub)
- Persistent chat history storage in Redis
- REST API to fetch chat history
- Scalable architecture suitable for multi-user environments

## Architecture
- **Spring Boot**: Application framework for rapid development and dependency injection.
- **WebSocket**: Handles real-time communication between clients and server.
- **Kafka**: Publishes and subscribes to chat messages for distributed delivery.
- **Redis**: Stores chat messages for history retrieval.
- **REST API**: Exposes endpoints for chat history.

## Project Structure
```
src/main/java/com/self/chatapplication/
  Main.java                  # Spring Boot entry point
  config/WebSocketConfig.java# WebSocket endpoint configuration
  config/kafka/              # Kafka producer/consumer
  impl/ChatWebSocketHandler.java # WebSocket message handler
  repo/ChatRedisRepository.java  # Redis message repository
  rest/ChatHistoryController.java# REST API for chat history
src/main/resources/
  application.properties     # Kafka & Redis configuration
```

## Getting Started
### Prerequisites
- Java 17+
- Maven
- Redis server running (default: localhost:6379)
- Kafka broker running (default: localhost:9092)

### Build & Run
1. Clone the repository.
2. Configure `src/main/resources/application.properties` if needed.
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Usage
- Connect to the WebSocket endpoint `/chat` for real-time messaging.
- Use the REST endpoint `/history?chatId=global` to fetch chat history.

## Extending
- Add authentication and user management for private/group chats.
- Integrate a frontend (e.g., React, Angular) for a complete chat UI.
- Scale Kafka and Redis for production workloads.


