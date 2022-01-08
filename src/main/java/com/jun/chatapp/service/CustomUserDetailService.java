package com.jun.chatapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jun.chatapp.model.User;
import com.jun.chatapp.model.UserDetailsModel;
import com.jun.chatapp.repository.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	
	@NonNull 
	private UserRepository userRepo;
	private List<GrantedAuthority> GRANTED_AUTHORITIES;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		return new UserDetailsModel(user.orElseThrow(() -> 
			new UsernameNotFoundException("Please check your credentials again")), GRANTED_AUTHORITIES);
	}

}
