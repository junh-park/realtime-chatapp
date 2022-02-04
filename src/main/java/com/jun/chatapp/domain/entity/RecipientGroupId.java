package com.jun.chatapp.domain.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecipientGroupId {
	
	@Column(name = "user_id")
	private int userId;
	@Column(name = "group_id")
	private int groupId;
	
	public int hashCode() {
		 return Objects.hash(userId, groupId);
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;
		if (!(obj instanceof MessageRecipientId)) return false;
		
		MessageRecipientId key = (MessageRecipientId) obj;
		return (key.getMessageId() == this.getGroupId() && key.getUserId() == this.getGroupId());
	}
}
