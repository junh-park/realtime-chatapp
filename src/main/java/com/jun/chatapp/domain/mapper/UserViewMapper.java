package com.jun.chatapp.domain.mapper;

import org.mapstruct.Mapper;

import com.jun.chatapp.domain.dto.UserDto;
import com.jun.chatapp.domain.model.User;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {
	public abstract UserDto toUserDto(User user);
}
