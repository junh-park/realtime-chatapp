package com.jun.chatapp.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.chatapp.config.security.JwtTokenUtil;
import com.jun.chatapp.domain.dto.AuthRequestDto;
import com.jun.chatapp.domain.dto.UserDto;
import com.jun.chatapp.domain.mapper.UserViewMapper;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;
import com.jun.chatapp.service.CustomUserDetailService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
	
	private MockMvc mockMvc;
	private AuthRequestDto authRequest;
	private JacksonTester<AuthRequestDto> jsonAuthRequest;
	private User user;
	private UserDto userDto;
	private String token;
	
	@Mock UserViewMapper userViewMapper;
	@Mock private CustomUserDetailService userService;
	@Mock private JwtTokenUtil jwtTokenUtil;
	@Mock AuthenticationManager authManager;
	
	@Mock
	Authentication authentication;
	
	@InjectMocks
	private AuthenticationController controller;

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
				.username("junpark").email("junpark@hotmail.com")
				.firstName("jun").lastName("park")
				.roles(Set.of(Role.USER))
				.enabled(true)
				.build();
		
		userDto = UserDto.builder()
					.username("junpark").email("junpark@hotmail.com")
					.build();
	}
	
	@Test
	public void when_login_should_return_ok() throws UnsupportedEncodingException, IOException, Exception {
		happyPathmockSetup();

		mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonAuthRequest.write(authRequest).getJson()))
		.andDo(print())
		.andExpect(status().isOk());
	}


	@Test
	public void login_returns_TokenReponseInHeader_UserInBody() throws IOException, Exception {
		happyPathmockSetup();
		
		mockMvc.perform(post("/auth")
				.content(jsonAuthRequest.write(authRequest).getJson())
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(header().string(HttpHeaders.AUTHORIZATION, token))
			.andExpect(jsonPath("$.id", is(user.getId())))
			.andExpect(jsonPath("$.username", is(user.getUsername())))
			.andExpect(jsonPath("$.email", is(user.getEmail())));
		}
	
	@Test
	public void badCredential_login_throws_exception() throws IOException, Exception {
		AuthRequestDto wrongRequest = AuthRequestDto.builder()
				.username("junpark").password("wrongPassword")
				.build();
		doThrow(BadCredentialsException.class).when(userService).authenticate(wrongRequest, authManager);
		
		assertThatThrownBy(() -> {
			mockMvc.perform(post("/auth")
					.content(jsonAuthRequest.write(wrongRequest).getJson())
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
		}).isInstanceOf(NestedServletException.class)
		.hasCauseExactlyInstanceOf(BadCredentialsException.class);
		}

	private void happyPathmockSetup() {
		token = "ranDomJwTToken";
//		when(userService.loadUserByUsername(authRequest.getUsername())).thenReturn(user);
		when(userService.authenticate(authRequest, authManager)).thenReturn(user);
		when(jwtTokenUtil.generateAccessToken(user)).thenReturn(token);
//		when(authManager.authenticate(anyObject())).thenReturn(authentication);
//		when(authentication.getPrincipal()).thenReturn(user);
		when(userViewMapper.toUserDto(user)).thenReturn(userDto);
	}
}
