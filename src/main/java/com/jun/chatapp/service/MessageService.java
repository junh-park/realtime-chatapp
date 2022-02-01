package com.jun.chatapp.service;

import java.util.List;

import com.jun.chatapp.domain.dto.MessageDto;

public interface MessageService {
	public void saveMessage(MessageDto message);
	public List<MessageDto> getMessages();
}
