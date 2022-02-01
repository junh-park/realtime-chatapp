package com.jun.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.chatapp.domain.entity.MessageRecipientEntity;
import com.jun.chatapp.domain.entity.MessageRecipientId;

public interface MessageRecipientRepository extends JpaRepository<MessageRecipientEntity, MessageRecipientId>{

}
