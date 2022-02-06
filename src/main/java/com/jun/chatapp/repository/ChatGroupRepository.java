package com.jun.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.chatapp.domain.entity.ChatGroupEntity;

public interface ChatGroupRepository extends JpaRepository<ChatGroupEntity, Integer>{

}
