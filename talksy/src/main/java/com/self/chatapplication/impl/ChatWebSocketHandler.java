package com.self.chatapplication.impl;

import com.self.chatapplication.config.kafka.ChatKafkaProducer;
import com.self.chatapplication.repo.ChatRedisRepository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Set;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Autowired
    private ChatKafkaProducer kafkaProducer;
    @Autowired
    private ChatRedisRepository redisRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String chatId = "global"; // For simplicity, using a single chat room
        String msg = message.getPayload();
        redisRepository.saveMessage(chatId, msg);
        kafkaProducer.sendMessage(msg);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    // Utility method for broadcasting messages
    public static void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    // Handle error
                }
            }
        }
    }
}
