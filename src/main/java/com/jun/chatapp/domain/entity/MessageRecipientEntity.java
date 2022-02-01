package com.jun.chatapp.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "user_message")
@IdClass(MessageRecipientId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MessageRecipientEntity implements Serializable {

	@Id @NotNull @NonNull 
	private int userId;
	
	@Id @NotNull @NonNull
	private int messageId;
	
}
