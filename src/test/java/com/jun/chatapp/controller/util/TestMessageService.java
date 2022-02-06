package com.jun.chatapp.controller.util;

import java.util.ArrayList;
import java.util.List;

import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.service.MessageService;

public class TestMessageService implements MessageService {
	
	private List<MessageEntity> messages = new ArrayList<>();
	
	public List<MessageEntity> getMessages() {
		return messages;
	}


	@Override
	public MessageEntity saveMessage(MessageEntity message) {
		this.messages.add(message);
		return message;
	}
}
