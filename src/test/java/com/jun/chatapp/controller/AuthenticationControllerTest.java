package com.jun.chatapp.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.chatapp.domain.dto.AuthRequestDto;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.mapper.UserMapper;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;
import com.jun.chatapp.service.CustomUserDetailService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
	
	private MockMvc mockMvc;
	private AuthRequestDto authRequest;
	private UserMapper userMapper;
	private JacksonTester<AuthRequestDto> jsonAuthRequest;
	
	@Mock
	private CustomUserDetailService userService;
	
	@InjectMocks
	private AuthenticationController controller;
	private User user;
	
	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.addFilter((request, response, chain) ->{
					request.setCharacterEncoding("utf-8");
					response.setCharacterEncoding("utf-8");
					chain.doFilter(request, response);
				}, "/*")
				.build();
		
		authRequest = new AuthRequestDto("junpark", "password");
		
		user = User.builder()
				.username("junpark").password("password").email("junpark@hotmail.com")
				.firstName("jun").lastName("park")
				.roles(Set.of(Role.USER))
				.enabled(true)
				.build();
	}
	
	@Test
	public void when_login_should_return_user() throws UnsupportedEncodingException, IOException, Exception {
		when(userService.loadUserByUsername("junpark")).thenReturn(user);
		
		mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonAuthRequest.write(authRequest).getJson()))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void login_returns_TokenReponse() throws IOException, Exception {
		mockMvc.perform(post("/auth")
				.content(jsonAuthRequest.write(authRequest).getJson())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.accessToken", notNullValue()))
			.andExpect(jsonPath("$.refreshToken", notNullValue()));
	}
}
