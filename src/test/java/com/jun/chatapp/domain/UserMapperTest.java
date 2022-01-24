package com.jun.chatapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jun.chatapp.domain.dto.UserDto;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.domain.mapper.UserMapperImpl;
import com.jun.chatapp.domain.mapper.UserViewMapper;
import com.jun.chatapp.domain.mapper.UserViewMapperImpl;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
		UserMapperImpl.class,
		UserViewMapperImpl.class
})
public class UserMapperTest {
	
	@Autowired
	UserMapper userMapper;

	@Autowired
	UserViewMapper userViewMapper;
	
	private UserEntity userEntity;
	private User user;
	private UserDto userDto;
	
	@BeforeEach
	public void setup() {
		userEntity = new UserEntity(1, "junpark", "password", "jun", "park", "jun@hotmail.com", Set.of(Role.USER), true);
		user = User.builder().id(1).username("junpark").password("password")
				.firstName("jun").lastName("park").email("jun@hotmail.com")
				.build();
		userDto = UserDto.builder()
				.id(1).username("junpark").email("jun@hotmail.com")
				.build();
	}
	
	@Test
	public void convertUserEntityToUserDto() {
		User mappedUserDto = userMapper.toUser(userEntity);
		
		assertThat(mappedUserDto).usingRecursiveComparison().isEqualTo(userEntity);
	}
	
	@Test
	public void convertUserToUserEntity() {
		UserEntity mappedUserEntity= userMapper.toUserEntity(user);
		
		assertThat(mappedUserEntity).usingRecursiveComparison()
			.ignoringFields("roles", "enabled").isEqualTo(user);
	}
	
	@Test
	public void convertUserToUserDto() {
		UserDto mappedUserDto= userViewMapper.toUserDto(user);
		
		assertThat(mappedUserDto).usingRecursiveComparison()
			.ignoringFields("firstName", "lastName", "password", "roles", "enabled").isEqualTo(userDto);
	}
}
