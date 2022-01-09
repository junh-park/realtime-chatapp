package com.jun.chatapp.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity @Table(name = "USER")
@Data @NoArgsConstructor
public class UserEntity implements UserDetails {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull @NonNull
	private String username;
	@NotNull @NonNull
	private String password;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialNonExpired;
	private boolean enabled;
	
	public UserEntity(@NotNull String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
