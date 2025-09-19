package com.self.chatapplication.rest;

import com.self.chatapplication.repo.ChatRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ChatHistoryController {
    @Autowired
    private ChatRedisRepository redisRepository;

    @GetMapping("/history")
    public List<String> getChatHistory(@RequestParam(defaultValue = "global") String chatId) {
        return redisRepository.getMessages(chatId);
    }
}
