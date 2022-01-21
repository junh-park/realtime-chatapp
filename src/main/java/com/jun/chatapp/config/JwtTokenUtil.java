package com.jun.chatapp.config;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
	private final String jwtSecret;
	private final String JwtIssuer;
	
	
}
