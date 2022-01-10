package com.jun.chatapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MessageModel {
	private UserEntity user;
	private String messageContent;
}
