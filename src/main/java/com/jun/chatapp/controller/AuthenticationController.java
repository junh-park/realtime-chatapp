package com.jun.chatapp.controller;

import org.springframework.http.HttpHeaders;
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
import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.domain.model.User;
import com.jun.chatapp.service.CustomUserDetailService;
import com.jun.chatapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final CustomUserDetailService userDetailService;
	private final AuthenticationManager authManager;
	private final JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/auth")
	public ResponseEntity<User> login(@RequestBody AuthRequestDto request) {
		User user = authenticate(request);
		String jwtToken = jwtTokenUtil.generateAccessToken(user);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, jwtToken)
				.body(user);
	}

	private User authenticate(AuthRequestDto request) {
		UserDetails userDetail = userDetailService.loadUserByUsername(request.getUsername());
		Authentication authenticate = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), userDetail.getPassword()));
		
		User user = (User) authenticate.getPrincipal();
		return user;
	}

}
