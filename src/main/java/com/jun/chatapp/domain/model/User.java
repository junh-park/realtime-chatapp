package com.jun.chatapp.domain.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.OperationNotSupportedException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User implements UserDetails {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	@Builder.Default
	private Set<Role> roles = new HashSet<>();
	private boolean enabled;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
	}
	
	public List<String> getRolesAsString() {
		return roles.stream()
				.map(role -> role.name())
				.collect(Collectors.toList());
	}

	// Since enabled takes higher order place, automatically updates other
	// validations at the same time
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
