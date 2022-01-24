package com.jun.chatapp.service;

import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;
import com.jun.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceJpa implements UserService {
	public final UserRepository userRepo;
	public final UserMapper userMapper;
	public final PasswordEncoder passwordEncoder;

	public int createUser(RegistrationRequestDto request) {
		if (usernameAlreadyExists(request.getUsername())) {
			throw new ValidationException("Username already exists");
		}

		UserEntity user = userMapper.toUserEntity(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEnabled(true);
		user.setRoles(Set.of(Role.USER));
		return userRepo.save(user).getId();
	}

	public User findByUsername(String username) {
		UserEntity userEntity = userRepo.findByUsername(username).orElseThrow(() ->
			new UsernameNotFoundException("Please check your user credentials again"));
		
		return userMapper.toUser(userEntity);
	}
	
	private boolean usernameAlreadyExists(String username) {
		return userRepo.findByUsername(username).isPresent();
	}
}