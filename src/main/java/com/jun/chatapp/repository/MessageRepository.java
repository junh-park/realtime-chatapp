package com.jun.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.chatapp.domain.entity.ChatGroupEntity;
import com.jun.chatapp.domain.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer>{
	
	MessageEntity findByMessageContaining(String message);

	List<MessageEntity> findAllByRecipients(ChatGroupEntity recipients);
}
