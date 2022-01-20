package com.jun.chatapp.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Authority implements GrantedAuthority, Serializable {
	public static final String ADMIN = "ADMIN";
	public static final String USER = "USER";

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String authority;
	
}
