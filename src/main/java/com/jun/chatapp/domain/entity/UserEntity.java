package com.jun.chatapp.domain.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity @Table(name = "USER")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity implements UserDetails, Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	@NotNull @NonNull
	private String username;
	@NotNull @NonNull
	private String password;
	private String firstName;
	private String lastName;
	@Email
	private String email;
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id")
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();
	private boolean enabled;
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getAuthority()))
				.collect(Collectors.toSet());
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
