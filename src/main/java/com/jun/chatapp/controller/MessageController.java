package com.jun.chatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.UserStorage;
import com.jun.chatapp.model.MessageModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class MessageController {
	 
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat/{to}")
	public void sendMessage(@DestinationVariable String to, MessageModel message) {
		log.info("handling sending message: " + message + " to: " + to);
		boolean isPresent = UserStorage.getInstance().getUsers().contains(to);
		if(isPresent) {
			simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
		}
		
	}
}
