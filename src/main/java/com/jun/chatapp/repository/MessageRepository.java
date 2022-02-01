package com.jun.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.chatapp.domain.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer>{
	
	MessageEntity findByMessageContaining(String message);
}
