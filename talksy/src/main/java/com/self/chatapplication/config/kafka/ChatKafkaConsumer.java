package com.self.chatapplication.config.kafka;

import com.self.chatapplication.impl.ChatWebSocketHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ChatKafkaConsumer {
    @KafkaListener(topics = "chat-messages", groupId = "chat-group")
    public void listen(String message) {
        // Broadcast received message to all WebSocket clients
        ChatWebSocketHandler.broadcast(message);
    }
}
