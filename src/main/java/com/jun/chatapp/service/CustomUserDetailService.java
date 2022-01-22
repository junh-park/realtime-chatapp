package com.jun.chatapp.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userService.findByUsername(username);
	}

}
