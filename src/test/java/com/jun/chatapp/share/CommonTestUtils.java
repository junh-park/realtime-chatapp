package com.jun.chatapp.share;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.jun.chatapp.domain.entity.ChatGroupEntity;
import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.domain.entity.MessageRecipientEntity;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.model.Role;

public abstract class CommonTestUtils {
	
	protected UserEntity jun;
	protected UserEntity dodo;
	protected UserEntity mimi;
	protected ChatGroupEntity group;
	protected MessageEntity message;
	protected MessageRecipientEntity messageRecipient;
	protected Optional<ChatGroupEntity> optGroup;
	
	public void setup() {
		Set<Role> role = new HashSet<>();
		role.add(Role.USER);
		
		jun = UserEntity.builder()
				.id(1).username("junpark").password("password")
				.firstName("jun").lastName("park").email("jun@hotmail.com")
				.roles(role).enabled(true).groups(new HashSet<>())
				.build();
		dodo = UserEntity.builder()
				.id(2).username("dorothy").password("password")
				.firstName("dodo").lastName("xu").email("dodo@hotmail.com")
				.roles(role).enabled(true).groups(new HashSet<>())
				.build();
		mimi = UserEntity.builder()
				.id(3).username("mimi").password("password")
				.firstName("mimi").lastName("xu").email("mimi@hotmail.com")
				.roles(role).enabled(true).groups(new HashSet<>())
				.build();
		
		message = MessageEntity.builder()
				.id(4).message("hello")
				.sender(jun)
				.recipients(group)
				.createdAt(LocalDateTime.now())
				.build();
		
		messageRecipient = MessageRecipientEntity.builder()
			.userId(2).messageId(4)
			.build();

		group = ChatGroupEntity.builder()
				.id(5).groupName("New conversation Group")
				.createdAt(LocalDateTime.now())
				.build();
		group.addUser(dodo);
		group.addUser(jun);
		
		optGroup = Optional.of(new ChatGroupEntity());
	}
}
