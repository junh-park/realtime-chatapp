package com.jun.chatapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jun.chatapp.config.JwtTokenFilter;
import com.jun.chatapp.config.JwtTokenUtil;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;

@ExtendWith(MockitoExtension.class)
public class FilterChainTest {

	private BCryptPasswordEncoder passwordEncoder;
	private MockFilterChain filterChain;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private User user;
	
	@Mock
	private SecurityContext securityContext;
	
	@Mock
	private UserDetailsService userDetailService;

	@Mock
	private JwtTokenUtil jwtTokenUtil;
	
	@InjectMocks
	private JwtTokenFilter filter;

	@BeforeEach
	public void setup() {
		 passwordEncoder = new BCryptPasswordEncoder();
		 request = new MockHttpServletRequest();
		 response = new MockHttpServletResponse();
		 filterChain = new MockFilterChain();

		 user = User.builder()
				 .username("junpark").email("user@email.com")
				 .password(passwordEncoder.encode("password"))
				 .firstName("jun").lastName("park")
				 .email("junpark@hotmail.com")
				 .roles(Set.of(Role.USER))
				 .build();

		 JwtTokenUtil tokenUtil= new JwtTokenUtil();
		 String token = "Bearer " + tokenUtil.generateAccessToken(user);
		 request.addHeader(HttpHeaders.AUTHORIZATION, token);

		 filter = new JwtTokenFilter(jwtTokenUtil, userDetailService);
	}
	
	@Test
	public void setAuthentication_In_SecurityContext() throws ServletException, IOException {
		SecurityContextHolder.setContext(securityContext);
		when(jwtTokenUtil.validate(anyString())).thenReturn(true);
		when(jwtTokenUtil.getUsernameFromToken(anyString())).thenReturn(user.getUsername());
		when(userDetailService.loadUserByUsername(anyString())).thenReturn(user);
		
		filter.doFilter(request, response, filterChain);

		ArgumentCaptor<UsernamePasswordAuthenticationToken> tokenArgCaptor= 
				ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
		verify(securityContext, times(1)).setAuthentication(tokenArgCaptor.capture());
		
		UsernamePasswordAuthenticationToken arg = tokenArgCaptor.getValue();
		assertThat(arg.getPrincipal()).isInstanceOf(User.class);
		assertThat(arg.getPrincipal()).usingRecursiveComparison().isEqualTo(user);
	}
}
