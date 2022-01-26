package com.jun.chatapp.share;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jun.chatapp.config.security.JwtTokenUtil;
import com.jun.chatapp.domain.mapper.JsonMapper;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenUtilTest {
	
	private User user;
	private JwtTokenUtil jwtTokenUtil;
	private String jwtSecret;
	
	@BeforeEach
	public void setup() {
		user = User.builder().username("junpark").roles(Set.of(Role.USER)).build();
		jwtSecret = "testJwtSecretKey";
		String jwtIssuer= "testJwtSecretKey";
		jwtTokenUtil = new JwtTokenUtil();
	}
	
	@Test
	public void create_jwtToken_using_username() {
		String jwtToken = jwtTokenUtil.generateAccessToken(user);
	
		String subject = Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(jwtToken)
			.getBody()
			.getSubject();
		
		assertThat(subject).isEqualTo("junpark");
	}
	
	@Test
	public void retrieve_username_from_token() {
		String jwtToken = jwtTokenUtil.generateAccessToken(user);
		
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		assertThat(username).isEqualTo(user.getUsername());
	}
	
	@Test
	public void validate_accessToken() {
		String jwtToken = jwtTokenUtil.generateAccessToken(user);
		
		boolean validity = jwtTokenUtil.validate(jwtToken);
		
		assertThat(validity).isTrue();
	}
	
	@Test
	public void validate_JwtToken_hasUserRole() {
		String jwtToken = jwtTokenUtil.generateAccessToken(user);
		
		Claims claims = Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(jwtToken)
			.getBody();
		
		String jsonRoles = JsonMapper.toJson(claims.get("roles"));
		List<Role> roleList = JsonMapper.fromJsonList(jsonRoles, Role.class);
		
		assertThat(roleList).contains(Role.USER).hasSize(1);
	}
	
}
