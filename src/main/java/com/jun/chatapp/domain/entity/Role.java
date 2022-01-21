package com.jun.chatapp.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor
public class Role implements GrantedAuthority {
	public static final String ADMIN = "ADMIN";
	public static final String USER = "USER";

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String role;
	
	public Role(String role) {
		this.role = role;
	}

	public String getAuthority() {
		return role;
	}
}
