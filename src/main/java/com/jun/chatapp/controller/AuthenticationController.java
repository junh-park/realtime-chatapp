package com.jun.chatapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	private final CustomUserDetailService userService;
	
	@PostMapping("/login")
	public String login() {
		return "";
	}
	
}
