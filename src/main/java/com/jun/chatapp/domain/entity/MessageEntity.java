package com.jun.chatapp.domain.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "Message")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageEntity {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String message;
	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime createdAt;
	@Column(name = "sender")
	private UserEntity sender;
	@Column(name = "chat_group")
	private ChatGroupEntity recipients;
}
