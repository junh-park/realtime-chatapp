package com.jun.chatapp.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity @Table(name = "MESSAGE_RECIPIENT")
@IdClass(RecipientGroupId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageRecipientEntity  {

	@Id @NotNull @NonNull 
	private int userId;
	
	@Id @NotNull @NonNull
	private int messageId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private ChatGroupEntity group;
	
}
