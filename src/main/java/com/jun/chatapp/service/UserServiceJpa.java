package com.jun.chatapp.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.domain.entity.Role;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceJpa implements UserService {
	public final UserRepository userRepo;
	public final UserMapper userMapper;

	public int createUser(RegistrationRequestDto request) {
		if (usernameAlreadyExists(request.getUsername())) {
			throw new ValidationException("Username already exists");
		}

		UserEntity user = userMapper.toUser(request);
		user.setEnabled(true);
		user.setRoles(Set.of(new Role(Role.USER)));
		return userRepo.save(user).getId();
	}

	public Optional<UserEntity> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	private boolean usernameAlreadyExists(String string) {
		return userRepo.findByUsername(string).isPresent();
	}
}