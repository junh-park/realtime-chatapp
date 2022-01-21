package com.jun.chatapp.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.domain.entity.Role;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userService.findByUsername(username);
		return user.orElseThrow(() -> new UsernameNotFoundException("Please check your credentials again"));
	}

}
