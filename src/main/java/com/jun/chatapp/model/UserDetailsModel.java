//package com.jun.chatapp.model;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.NonNull;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserDetailsModel implements UserDetails {
//	
//	@NonNull
//	private UserEntity user;
//
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null;
//	}
//
//	public boolean isAccountNonExpired() {
//		return false;
//	}
//
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	public boolean isCredentialsNonExpired() {
//		return false;
//	}
//
//	public boolean isEnabled() {
//		return false;
//	}
//
//	public String getPassword() {
//		return user.getPassword();
//	}
//
//	public String getUsername() {
//		return user.getUsername();
//	}
//}
