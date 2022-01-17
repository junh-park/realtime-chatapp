package com.jun.chatapp.domain.mapper;

import org.mapstruct.Mapper;

import com.jun.chatapp.domain.dto.UserDto;
import com.jun.chatapp.domain.entity.UserEntity;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	public abstract UserDto toUserDto(UserEntity user);
	
	public abstract UserEntity toUser(UserDto userDto);
	
}
