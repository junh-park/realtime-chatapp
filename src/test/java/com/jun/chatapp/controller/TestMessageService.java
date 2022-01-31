package com.jun.chatapp.controller;

import java.util.ArrayList;
import java.util.List;

import com.jun.chatapp.service.MessageService;

public class TestMessageService implements MessageService {
	
	private List<String> messages = new ArrayList<>();
	
	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	public List<String> getMessages() {
		return messages;
	}
}
