package com.jun.chatapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.dto.AuthRequestDto;
import com.jun.chatapp.domain.model.User;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Lazy
	@Autowired
	private UserService userService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userService.findByUsername(username);
	}

	public User authenticate(AuthRequestDto request, AuthenticationManager authManager) {
		try {
			Authentication authenticate = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			
			User user = (User) authenticate.getPrincipal();
			return user;
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Please check your credentials");
		}
	}

}
