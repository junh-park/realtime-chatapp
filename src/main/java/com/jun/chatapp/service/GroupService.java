package com.jun.chatapp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.entity.GroupEntity;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

	private final GroupRepository groupRepo;
	
	public GroupEntity createConversation(UserEntity user1, UserEntity user2) {
		GroupEntity group = new GroupEntity();
		group.setGroupName("New group conversation");
		group.setCreatedAt(LocalDateTime.now());
		group.getUsers().add(user1);
		group.getUsers().add(user2);
		
		return groupRepo.save(group);
	}

	public GroupEntity addUserToExistingGroup(GroupEntity groupEntity, UserEntity newUser) {
		GroupEntity group = groupRepo.findById(groupEntity.getId())
				.orElseThrow(() -> new EntityNotFoundException());
		group.addUser(newUser);
		return groupRepo.save(group);
	}

}
