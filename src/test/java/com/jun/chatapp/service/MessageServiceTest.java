package com.jun.chatapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jun.chatapp.domain.entity.ChatGroupEntity;
import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.repository.MessageRepository;
import com.jun.chatapp.share.CommonTestUtils;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest extends CommonTestUtils {
	
	@Mock
	private MessageRepository messageRepo;
	
	@InjectMocks
	private MessageServiceImpl messageService;


	private MessageEntity message2;

	private List<MessageEntity> messages;
	
	@BeforeEach
	public void setup() {
		super.setup();
		messages = new ArrayList<>();
		message2 = MessageEntity.builder()
				.id(6).createdAt(LocalDateTime.now()).sender(dodo)
				.recipients(group).message("hi how are you doing")
				.build();
		messages.add(message);
		messages.add(message2);
	}

	@Test
	public void messageShouldBeSaved_whenANewMessageIsReceived() {
		when(messageRepo.save(message)).thenReturn(message);
		
		MessageEntity returnedMessage = messageService.saveMessage(message);
		
		assertThat(returnedMessage).usingRecursiveComparison().ignoringFields("id").isEqualTo(message);
	}
	
	@Test
	public void shouldReturnAllMessages_givenChatGroup() {
		when(messageRepo.findAllByRecipients(any(ChatGroupEntity.class))).thenReturn(messages);
		
		List<MessageEntity> returnedMessage = messageService.getAllMessagesInChatGroup(group);
		
		assertThat(returnedMessage).hasSize(2).contains(message, message2);
	}
	
	
}
