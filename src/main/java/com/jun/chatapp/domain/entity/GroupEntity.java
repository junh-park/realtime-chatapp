package com.jun.chatapp.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "CHAT_GROUP")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor@Builder
public class GroupEntity {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_id")
	private int id;
	private String groupName;
	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	@CreationTimestamp()
	private LocalDateTime createdAt;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "user_group",
		joinColumns = @JoinColumn(name = "group_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<UserEntity> users = new HashSet<>();
	
	public void addUser(UserEntity user) {
		this.users.add(user);
		user.getGroups().add(this);
	}
	
	public void removeUser(UserEntity user) {
		this.users.remove(user);
		user.getGroups().remove(this);
	}
	
	public int hashCode() {
		return Objects.hash(id, groupName);
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;
		if (!(obj instanceof GroupEntity)) return false;
		
		GroupEntity other = (GroupEntity) obj;
		return (other.getId() == this.getId());
	}
}
