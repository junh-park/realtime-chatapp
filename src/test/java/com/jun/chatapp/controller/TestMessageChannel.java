package com.jun.chatapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.AbstractSubscribableChannel;

public class TestMessageChannel extends AbstractSubscribableChannel {
	
	private final List<Message<?>> messages = new ArrayList<>();
		
	public List<Message<?>> getMessages() {
		return messages;
	}
	
	protected boolean sendInternal(Message<?> message, long timeout) {
		this.messages.add(message);
		return true;
	}

}
