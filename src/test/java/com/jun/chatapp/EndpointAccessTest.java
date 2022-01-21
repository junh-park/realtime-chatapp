package com.jun.chatapp;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.chatapp.domain.dto.RegistrationRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class EndpointAccessTest {
	@Autowired
	private MockMvc mockMvc;
	private RegistrationRequestDto request;
	
	@BeforeEach
	public void setup() {
		request = RegistrationRequestDto.builder()
				.username("junpark").password("password").email("junpark@hotmail.com").firstName("jun").lastName("park")
				.build();
	}
	
	@Test
	public void register_test() throws JsonProcessingException, Exception {
		mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$", is(1)));
		
	}
}
