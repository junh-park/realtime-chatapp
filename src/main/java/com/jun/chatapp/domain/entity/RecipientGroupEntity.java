package com.jun.chatapp.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "RECIPIENT_GROUP")
@IdClass(MessageRecipientId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RecipientGroupEntity {
	
	@Id
	private int userId;
	
	@Id
	private int groupId;
	
	@MapsId("userId")
	@ManyToOne
	@JoinColumn(name = "user_id")
	UserEntity user;

	@MapsId("groupId")
	@ManyToOne
	@JoinColumn(name = "group_id")
	ChatGroupEntity group;
	
}
