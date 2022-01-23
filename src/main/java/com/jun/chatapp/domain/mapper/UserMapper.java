package com.jun.chatapp.domain.mapper;

import org.mapstruct.Mapper;

import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.model.User;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	public abstract User toUser(UserEntity user);
	
	public abstract UserEntity toUserEntity(User userDto);
	
	public abstract UserEntity toUserEntity(RegistrationRequestDto request);
	
}
