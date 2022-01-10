package com.jun.chatapp.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "Messages")
@Data @NoArgsConstructor 
public class MessageEntity {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String message;
	private Long userId;
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;
	
	public MessageEntity(String message, Long userId, Timestamp createdAt) {
		this.message = message;
		this.userId = userId;
		this.createdAt = createdAt;
	}
}
