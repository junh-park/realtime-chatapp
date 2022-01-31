package com.jun.chatapp.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.domain.dto.MessageDto;
import com.jun.chatapp.service.MessageService;
import com.jun.chatapp.service.MessageServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessageController {
	private final MessageService messageService;
	
    public MessageController(MessageService messageService) {
    	this.messageService = messageService;
	}

	@SubscribeMapping("/message")
    public String broadcastMessage(Principal principal) {
		log.debug("Hello " + principal.getName());
    	return "Hello " + principal.getName();
    }
	
	@MessageMapping("/chat")
	public MessageDto sendMessage(@Payload MessageDto content) {
		log.debug(content.toString());
		return content;
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
