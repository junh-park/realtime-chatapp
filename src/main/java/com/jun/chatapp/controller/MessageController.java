package com.jun.chatapp.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.domain.dto.MessageDto;
import com.jun.chatapp.domain.mapper.MessageMapper;
import com.jun.chatapp.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessageController {
	private final MessageService messageService;
	private final MessageMapper mapper;
	
    public MessageController(MessageService messageService, MessageMapper mapper) {
    	this.messageService = messageService;
		this.mapper = mapper;
	}

	@SubscribeMapping("/message")
    public String broadcastMessage(Principal principal) {
		log.debug("Hello " + principal.getName());
    	return "Hello " + principal.getName();
    }
	
	@MessageMapping("/chat")
	public List<MessageDto> sendMessage(@Payload MessageDto content, Principal principal) {
		log.debug(content.toString());
		messageService.saveMessage(mapper.toMessage(content));
		return mapper.toMessageDtoList(messageService.getMessages());
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
