package com.self.chatapplication.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ChatRedisRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void saveMessage(String chatId, String message) {
        redisTemplate.opsForList().rightPush(chatId, message);
    }

    public java.util.List<String> getMessages(String chatId) {
        return redisTemplate.opsForList().range(chatId, 0, -1);
    }
}

