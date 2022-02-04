package com.jun.chatapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.jun.chatapp.domain.entity.GroupEntity;
import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.domain.entity.MessageRecipientEntity;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.repository.GroupRepository;

@ExtendWith(MockitoExtension.class)
@Transactional
public class GroupServiceTest {
	private GroupService groupService;
	private MessageEntity message;
	private MessageRecipientEntity messageRecipient;
	private UserEntity jun;
	private UserEntity dodo;
	private UserEntity mimi;
	private GroupEntity group;

	@Mock
	private GroupRepository groupRepository;
	private Optional<GroupEntity> optGroup;
	
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

		group = GroupEntity.builder().id(1).groupName("New conversation Group")
				.createdAt(LocalDateTime.now()).build();
		group.addUser(dodo);
		group.addUser(jun);
		
		optGroup = Optional.of(new GroupEntity());
	}
	
	@Test
	public void createGroup_when_2UsersAreEngaged() {
		when(groupRepository.save(any(GroupEntity.class))).thenReturn(group);
		
		GroupEntity newGroup = groupService.createConversation(jun, dodo);
		
		assertThat(newGroup.getUsers()).contains(jun, dodo);
	}
	
	@Test
	public void addUserToGroup_whenNewUserIsAdded() {
		group.addUser(mimi);
		when(groupRepository.findById(any(Integer.class))).thenReturn(Optional.of(group));
		when(groupRepository.save(any(GroupEntity.class))).thenReturn(group);

		GroupEntity updatedGroup = groupService.addUserToExistingGroup(group, mimi);
	
		assertThat(updatedGroup.getUsers()).hasSize(3);
		assertThat(updatedGroup.getUsers()).contains(jun, dodo, mimi);
	}
	
	@Test
	public void throwException_whenNewUserIsAdded_toAddUserToGroup_andNoGroupIsFound() throws Throwable {
		group.addUser(mimi);
		when(groupRepository.findById(any(Integer.class)).orElseThrow(() -> new EntityNotFoundException())).thenThrow(EntityNotFoundException.class);
//		when(optGroup.orElseThrow(() -> new EntityNotFoundException())).thenThrow(EntityNotFoundException.class);
		when(groupRepository.save(any(GroupEntity.class))).thenThrow(IllegalArgumentException.class);

		GroupEntity updatedGroup = groupService.addUserToExistingGroup(group, mimi);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> groupService.addUserToExistingGroup(group, mimi));
	}
	
}
