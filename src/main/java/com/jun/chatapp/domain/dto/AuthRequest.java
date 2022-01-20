package com.jun.chatapp.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Getter @Builder
public class AuthRequest {
	@NonNull
	private String username;
	@NonNull
	private String password;
}
