package com.jun.chatapp.domain.dto;

import lombok.Data;
import lombok.NonNull;

@Data 
public class AuthRequest {
	@NonNull
	private String username;
	@NonNull
	private String password;
}
