package com.jun.chatapp.domain.dto;

import com.jun.chatapp.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MessageDto {
	private UserEntity user;
	private String messageContent;
}
