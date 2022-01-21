package com.jun.chatapp.service;

import java.util.Optional;

import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.domain.entity.UserEntity;

public interface UserService {

	public int createUser(RegistrationRequestDto request);

	public Optional<UserEntity> findByUsername(String username);

}
