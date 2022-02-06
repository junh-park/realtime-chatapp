package com.jun.chatapp.service;

import java.util.List;

import com.jun.chatapp.domain.dto.MessageDto;
import com.jun.chatapp.domain.entity.MessageEntity;

public interface MessageService {
	public MessageEntity saveMessage(MessageEntity message);
	public List<MessageEntity> getMessages();
}
