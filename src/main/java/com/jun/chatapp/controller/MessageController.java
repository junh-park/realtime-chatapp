package com.jun.chatapp.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jun.chatapp.model.MessageModel;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/messages")
//    @SendTo("/topic/chat.messages")
    public String broadcastMessage(@Payload MessageModel message) {
        return message.getMessageContent();

    }

    private String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String timeNow = formatter.format(LocalDateTime.now());
        System.out.println(timeNow);
        return timeNow;
    }
}
