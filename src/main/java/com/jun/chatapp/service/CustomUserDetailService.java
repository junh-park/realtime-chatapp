package com.jun.chatapp.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	@Lazy
	private UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userService.findByUsername(username);
	}

}
