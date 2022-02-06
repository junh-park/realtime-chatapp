package com.jun.chatapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.dto.MessageDto;
import com.jun.chatapp.domain.entity.ChatGroupEntity;
import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
	
	private final MessageRepository messageRepo;

	public MessageEntity saveMessage(MessageEntity message) {
		return messageRepo.save(message);
	}

	public List<MessageEntity> getMessages() {
		return null;
	}

	public List<MessageEntity> getAllMessagesInChatGroup(ChatGroupEntity group) {
		return messageRepo.findAllByRecipients(group);
	}

}
