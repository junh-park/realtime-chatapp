package com.jun.chatapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.chatapp.domain.dto.AuthRequestDto;
import com.jun.chatapp.domain.dto.RegistrationRequestDto;
import com.jun.chatapp.service.CustomUserDetailService;
import com.jun.chatapp.service.UserService;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {
	
	private MockMvc mockMvc;
	private RegistrationRequestDto regRequest;
	private JacksonTester<RegistrationRequestDto> jsonRegisterRequest;
	@Mock
	private UserService userService;
	
	@InjectMocks
	private RegisterController controller;
	
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
		
		regRequest = RegistrationRequestDto.builder()
				.username("junpark").password("password").email("junpark@hotmail.com").firstName("jun").lastName("park")
				.build();
	}
	
	@Test
	public void when_creating_A_New_User_should_return_Created() throws IOException, Exception {
		MockHttpServletResponse response = mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRegisterRequest.write(regRequest).getJson()))
			.andDo(print())
			.andReturn().getResponse();
	
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	@Test
	public void when_creating_A_New_User_should_return_Id() throws IOException, Exception {
		when(userService.createUser(regRequest)).thenReturn(1);
		
		String returnedId = mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRegisterRequest.write(regRequest).getJson()))
			.andDo(print())
			.andReturn().getResponse().getContentAsString();
		
		assertThat(returnedId).isEqualTo("1");
	}
}
