package com.jun.chatapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.chatapp.domain.dto.AuthRequestDto;
import com.jun.chatapp.domain.dto.RegistrationRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class EndpointAccessTest {
	@Autowired
	private MockMvc mockMvc;
	
	private RegistrationRequestDto regRequest;
	private AuthRequestDto authRequest;
	private AuthRequestDto badAuthRequest;
	
	@BeforeEach
	public void setup() {
		regRequest = RegistrationRequestDto.builder()
				.username("junpark").password("password").email("junpark@hotmail.com")
				.firstName("jun").lastName("park")
				.build();
		
		authRequest = AuthRequestDto.builder()
				.username("junpark").password("password")
				.build();
		
		badAuthRequest = AuthRequestDto.builder()
				.username("junpark").password("wrongPassword")
				.build();
	}
	
	@Test
	@Transactional
	public void register_test() throws JsonProcessingException, Exception {
		register()
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	@Test
	@Transactional
	public void login_test() throws JsonProcessingException, Exception {
		register();
		
		Exception resolvedException = mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(authRequest)))
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn().getResolvedException();
		System.out.println(resolvedException);
	}
	
	@Test
	@Transactional
	public void badCredential_login_test() throws JsonProcessingException, Exception {
		register();
		
		mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(badAuthRequest)))
		.andExpect(status().isUnauthorized());
	}

	private ResultActions register() throws Exception, JsonProcessingException {
		return mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(regRequest)));
	}
}
