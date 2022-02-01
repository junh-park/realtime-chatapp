package com.jun.chatapp.controller.util;

import java.util.ArrayList;
import java.util.List;

import com.jun.chatapp.domain.dto.MessageDto;
import com.jun.chatapp.service.MessageService;

public class TestMessageService implements MessageService {
	
	private List<MessageDto> messages = new ArrayList<>();
	
	public List<MessageDto> getMessages() {
		return messages;
	}

	public void saveMessage(MessageDto message) {
		this.messages.add(message);
		
	}
}
