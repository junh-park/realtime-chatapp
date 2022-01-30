package com.jun.chatapp.config.security;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {

	public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

//	@Value("${jwt.secret}")
	private final String jwtSecret = "makeItVerySecret";
//	@Value("${jwt.issuer}")
	private final String jwtIssuer = "JunPark";

	public String generateAccessToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("roles", user.getRoles());
		claims.put("email", user.getEmail());
		claims.put("name", user.getFirstName() + " " + user.getLastName());

		return Jwts.builder()
				.setClaims(claims)
				.setIssuer(jwtIssuer)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() * JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUsernameFromToken(String jwtToken) {
		return getClaimsFromToken(jwtToken)
				.getSubject();
	}
	
	public Authentication getUserFromToken(String jwtToken) {
		Claims claims = getClaimsFromToken(jwtToken);
		
		List<String> roles = claims.get("roles", List.class);
		User user = User.builder()
				.username(claims.getSubject())
				.email(claims.get("email", String.class))
				.password("")
				.roles(roles.stream()
						.map(role -> Role.valueOf(role))
						.collect(Collectors.toSet()))
				.build();
		List<GrantedAuthority> auths = AuthorityUtils
				.commaSeparatedStringToAuthorityList(roles.toString());

		return new UsernamePasswordAuthenticationToken(user, "", auths);
	}

	private Claims getClaimsFromToken(String jwtToken) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(jwtToken)
				.getBody();
	}

	public boolean validate(String jwtToken) {
		try {
			Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(jwtToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature - {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token - {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token - {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token - {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty - {}", e.getMessage());
		}
		return false;
	}

}
