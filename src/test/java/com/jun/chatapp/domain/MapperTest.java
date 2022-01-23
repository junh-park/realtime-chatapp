package com.jun.chatapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.domain.mapper.UserMapperImpl;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
		UserMapperImpl.class
})
public class MapperTest {
	
	@Autowired
	UserMapper userMapper;
	
	private UserEntity userEntity;
	private User userDto;
	
	@BeforeEach
	public void setup() {
		userEntity = new UserEntity(1, "user", "password", "jun", "park", "jun@hotmail.com", Set.of(Role.USER), true);
		userDto = User.builder().id(1).username("user").password("password")
				.firstName("jun").lastName("park").email("jun@hotmail.com")
				.build();
	}
	
	@Test
	public void convertUserEntityToUserDto() {
		User mappedUserDto = userMapper.toUser(userEntity);
		
		assertThat(mappedUserDto).usingRecursiveComparison().isEqualTo(userEntity);
	}
	
	@Test
	public void convertUserDtoToUserEntity() {
		UserEntity mappedUserEntity= userMapper.toUserEntity(userDto);
		
		assertThat(mappedUserEntity).usingRecursiveComparison()
			.ignoringFields("roles", "enabled").isEqualTo(userDto);
	}
}
