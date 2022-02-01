package com.jun.chatapp.domain.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "Message")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp cratedAt;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn()
	private Set<UserEntity> users = new HashSet<>();
}
