package com.jun.chatapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.chatapp.domain.dto.AuthRequest;

public class AuthenticationControllerTest {
	
	private MockMvc mockMvc;
	private AuthRequest authRequest;
	private JacksonTester<AuthRequest> jsonAuthRequest;
	private AuthenticationController controller;
	
	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.build();
		authRequest = AuthRequest.builder()
				.username("junpark").password("junpark")
				.build();
	}
	
	@Test
	public void when_Login_should_return_ok() throws IOException, Exception {
		MockHttpServletResponse response = mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonAuthRequest.write(authRequest).getJson()))
			.andReturn().getResponse();
	
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
	}
}
