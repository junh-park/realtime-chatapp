package com.jun.chatapp.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @Builder
public class UserDto {
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
}
