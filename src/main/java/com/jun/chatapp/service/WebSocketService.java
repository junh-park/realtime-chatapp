package com.jun.chatapp.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.dto.MessageDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebSocketService {
	final private SimpMessagingTemplate template;
	
	public void notifyUsers(final MessageDto message) {
		MessageDto notification = new MessageDto(null, message.getMessageContent());
		template.convertAndSend("/topic/messages", notification);
	}
}
