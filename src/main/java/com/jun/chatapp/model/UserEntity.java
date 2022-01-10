package com.jun.chatapp.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
	@OneToMany(targetEntity = Roles.class, fetch = FetchType.EAGER)
	private Set<Roles> authorities = new HashSet<>();
	private boolean enabled;
	
	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
		this.enabled = true;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	//Since enabled takes higher order place, automatically updates other validations at the same time
	public boolean isAccountNonExpired() {
		return enabled;
	}
	public boolean isAccountNonLocked() {
		return enabled;
	}
	public boolean isCredentialsNonExpired() {
		return enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
}
