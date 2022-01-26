package com.jun.chatapp.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.config.security.JwtTokenUtil;
import com.jun.chatapp.domain.dto.AuthRequestDto;
import com.jun.chatapp.domain.dto.UserDto;
import com.jun.chatapp.domain.mapper.UserViewMapper;
import com.jun.chatapp.domain.model.User;
import com.jun.chatapp.service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final CustomUserDetailService userDetailService;
	private final AuthenticationManager authManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserViewMapper userViewMapper;
	
	@PostMapping("/auth")
	public ResponseEntity<UserDto> login(@RequestBody AuthRequestDto request) {
		User user = userDetailService.authenticate(request, authManager);
		String jwtToken = jwtTokenUtil.generateAccessToken(user);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, jwtToken)
				.body(userViewMapper.toUserDto(user));
	}



}
