package com.jun.chatapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jun.chatapp.config.JwtTokenUtil;
import com.jun.chatapp.domain.dto.AuthRequestDto;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.service.CustomUserDetailService;
import com.jun.chatapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	private final CustomUserDetailService userDetailService;
	private final UserService userService;
	private AuthenticationManager authManager;
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequestDto request) {
		UserDetails user = userDetailService.loadUserByUsername(request.getUsername());
		Authentication authenticate = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), user.getPassword()));
		UserEntity principal = (UserEntity) authenticate.getPrincipal();
		
		return new ResponseEntity(user, HttpStatus.OK);
	}

}
