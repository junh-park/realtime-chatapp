package com.jun.chatapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jun.chatapp.domain.entity.ChatGroupEntity;
import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.domain.entity.MessageRecipientEntity;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.repository.ChatGroupRepository;
import com.jun.chatapp.share.CommonTestUtils;

@ExtendWith(MockitoExtension.class)
@Transactional
public class GroupServiceTest extends CommonTestUtils {
	private GroupService groupService;

	@Mock
	private ChatGroupRepository groupRepository;
	
	@BeforeEach
	public void setup() {
		groupService = new GroupService(groupRepository);
		super.setup();
	}
	
	@Test
	public void createGroup_when_2UsersAreEngaged() {
		when(groupRepository.save(any(ChatGroupEntity.class))).thenReturn(group);
		
		ChatGroupEntity newGroup = groupService.createConversation(jun, dodo);
		
		assertThat(newGroup.getUsers()).contains(jun, dodo);
	}
	
	@Test
	public void addUserToGroup_whenNewUserIsAdded() {
		group.addUser(mimi);
		when(groupRepository.findById(any(Integer.class))).thenReturn(Optional.of(group));
		when(groupRepository.save(any(ChatGroupEntity.class))).thenReturn(group);

		ChatGroupEntity updatedGroup = groupService.addUserToExistingGroup(group, mimi);
	
		assertThat(updatedGroup.getUsers()).hasSize(3);
		assertThat(updatedGroup.getUsers()).contains(jun, dodo, mimi);
	}
	
	@Test
	public void throwException_whenNewUserIsAdded_toAddUserToGroup_andNoGroupIsFound() throws Throwable {
		assertThatThrownBy(() -> groupService.addUserToExistingGroup(group, mimi))
			.isInstanceOf(EntityNotFoundException.class);
	}
	
}
