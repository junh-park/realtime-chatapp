package com.jun.chatapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RegisterController {
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registration(@RequestBody RegistrationRequestDto request) {
		int returnedId = userService.createUser(request);
		
		return new ResponseEntity(returnedId, HttpStatus.CREATED);
	}
	
}
