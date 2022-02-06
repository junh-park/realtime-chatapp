package com.jun.chatapp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jun.chatapp.domain.entity.ChatGroupEntity;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.repository.ChatGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {

	private final ChatGroupRepository groupRepo;
	
	public ChatGroupEntity createConversation(UserEntity user1, UserEntity user2) {
		ChatGroupEntity group = new ChatGroupEntity();
		group.setGroupName("New group conversation");
		group.setCreatedAt(LocalDateTime.now());
		group.addUser(user1);
		group.addUser(user2);
		
		return groupRepo.save(group);
	}

	public ChatGroupEntity addUserToExistingGroup(ChatGroupEntity groupEntity, UserEntity newUser) {
		ChatGroupEntity group = groupRepo.findById(groupEntity.getId())
				.orElseThrow(() -> new EntityNotFoundException());
		group.addUser(newUser);
		return groupRepo.save(group);
	}

}
