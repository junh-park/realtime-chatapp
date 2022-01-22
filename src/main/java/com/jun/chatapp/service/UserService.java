package com.jun.chatapp.service;

import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.domain.model.User;

public interface UserService {

	public int createUser(RegistrationRequestDto request);

	public User findByUsername(String username);

}
