package com.jun.chatapp.domain.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.jun.chatapp.domain.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity @Table(name = "USER")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity {
	
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
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();
	private boolean enabled;
	
	@ManyToMany(mappedBy = "users")
	private Set<GroupEntity> groups= new HashSet<>();
	
	public void addGroup(GroupEntity group) {
		this.groups.add(group);
		group.getUsers().add(this);
	}
	
	public void removeUser(GroupEntity group) {
		this.groups.remove(group);
		group.getUsers().remove(this);
	}
	
	public int hashCode() {
		return Objects.hash(username, email);
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;
		if (!(obj instanceof UserEntity)) return false;
		
		UserEntity other = (UserEntity) obj;
		return (other.getUsername().equals(this.getUsername()) && other.getEmail().equals(this.getEmail()));
	}
}
