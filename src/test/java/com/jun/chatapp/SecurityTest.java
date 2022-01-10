package com.jun.chatapp;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SecurityTest {

	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@Test
	public void requestingIndexPageReturnsIndexPage() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index.html"));
	}
	
	@Test
	public void requestingPrivateUrlBeforeAuthenticatingReturnsLoginPage() throws Exception {
		mvc.perform(get("/chat"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("http://localhost/login.html"));
	}
	
	@Test
	public void formlogintest() throws Exception {
		mvc.perform(formLogin("/login").password("password1"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("http://localhost/chat"));
	}

}
