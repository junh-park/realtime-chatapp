package com.jun.chatapp.domain.dto;

import com.jun.chatapp.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MessageModel {
	private UserEntity user;
	private String messageContent;
}
