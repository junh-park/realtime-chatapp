package com.jun.chatapp.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.jun.chatapp.model.MessageModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebSocketService {
	final private SimpMessagingTemplate template;
	
	public void notifyUsers(final MessageModel message) {
		MessageModel notification = new MessageModel(null, message.getMessageContent());
		template.convertAndSend("/topic/messages", notification);
	}
}
