package com.jun.chatapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.jun.chatapp.domain.dto.MessageDto;

@Controller
public class MessageController {
    @MessageMapping("/messages")
//    @SendTo("/topic/chat.messages")
//    @SendTo(value = {"/topic/chat.messages"})
    public MessageDto broadcastMessage(@Payload MessageDto message) {
    	MessageDto messageModel = new MessageDto();
    	messageModel.setMessageContent(addTimestamp() + message.getMessageContent());
    	return messageModel;
    }

    private String addTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return new StringBuilder()
        		.append("[")
        		.append(formatter.format(LocalDateTime.now()))
        		.append("]: ")
        		.toString(); 
    }
}
