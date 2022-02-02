package com.jun.chatapp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.jun.chatapp.domain.entity.GroupEntity;
import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.domain.entity.MessageRecipientEntity;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.repository.GroupRepository;

@DataJpaTest
public class GroupServiceTest {
	private GroupService groupService;
	private MessageEntity message;
	private MessageRecipientEntity messageRecipient;
	private UserEntity jun;
	private UserEntity dodo;
	private UserEntity mimi;

	@Autowired
	private GroupRepository groupRepository;
	
	@BeforeEach
	public void setup() {
		groupService = new GroupService(groupRepository);
		Set<Role> role = new HashSet<>();
		role.add(Role.USER);
		
		jun = UserEntity.builder().username("junpark").password("password")
				.firstName("jun").lastName("park").email("jun@hotmail.com")
				.roles(role).enabled(true).groups(new HashSet<>())
				.build();
		dodo = UserEntity.builder().username("dorothy").password("password")
				.firstName("dodo").lastName("xu").email("dodo@hotmail.com")
				.roles(role).enabled(true).groups(new HashSet<>())
				.build();
		mimi = UserEntity.builder().username("mimi").password("password")
				.firstName("mimi").lastName("xu").email("mimi@hotmail.com")
				.roles(role).enabled(true).groups(new HashSet<>())
				.build();
		message = MessageEntity.builder()
				.message("hello I am Jun")
				.senderId(jun.getId())
				.build();
		messageRecipient = MessageRecipientEntity.builder()
			.userId(1).messageId(1)
			.build();
	}
	
	@Test
	public void createGroup_when_2UsersAreEngaged() {
		GroupEntity groupEntity = groupService.createConversation(jun, dodo);
		
		assertThat(groupEntity.getUsers()).contains(jun, dodo);
	}
	
	@Test
	public void addUserToGroup_whenNewUserIsAdded() {
		GroupEntity groupEntity = groupService.createConversation(jun, dodo);

		GroupEntity updatedGroup = groupService.addUserToExistingGroup(groupEntity, mimi);
	
		assertThat(updatedGroup.getUsers()).hasSize(3);
		assertThat(updatedGroup.getUsers()).contains(jun, dodo, mimi);
	}
	
}
