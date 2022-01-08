package com.jun.chatapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.jun.chatapp.model.MessageModel;

@Controller
public class MessageController {
    @MessageMapping("/messages")
//    @SendTo("/topic/chat.messages")
    public MessageModel broadcastMessage(@Payload MessageModel message) {
    	MessageModel messageModel = new MessageModel();
    	messageModel.setMessageContent(addTimestamp() + message.getMessageContent());
    	return messageModel;
    }

    private String addTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return new StringBuilder().append("[").append(formatter.format(LocalDateTime.now())).append("]: ").toString(); 
    }
}
