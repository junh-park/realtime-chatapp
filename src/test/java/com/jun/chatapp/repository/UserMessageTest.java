package com.jun.chatapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.jun.chatapp.domain.dto.UserDto;
import com.jun.chatapp.domain.entity.MessageEntity;
import com.jun.chatapp.domain.entity.MessageRecipientEntity;
import com.jun.chatapp.domain.entity.UserEntity;
import com.jun.chatapp.domain.model.Role;
import com.jun.chatapp.domain.model.User;

@DataJpaTest
//@ActiveProfiles("test")
//@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserMessageTest {
	
	private UserEntity user;
	private UserDto userDto;
	private MessageEntity message;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private MessageRepository messageRepo;
	@Autowired 
	private MessageRecipientRepository messageRecipientRepo;
	@Autowired
	private EntityManager em;
	private MessageRecipientEntity messageRecipient;
	
	@BeforeEach
	public void setup() {
		user = UserEntity.builder().username("junpark").password("password")
				.firstName("jun").lastName("park").email("jun@hotmail.com")
				.roles(Set.of(Role.USER)).enabled(true)
				.build();
		message = MessageEntity.builder()
				.message("hello I am Jun")
				.senderId(user.getId())
				.build();
		messageRecipient = MessageRecipientEntity.builder()
			.userId(1).messageId(1)
			.build();
		
		em.persist(user);
		em.persist(message);
		em.persist(messageRecipient);
		}

	@Test
	public void reposShouldSaveUserAndMessageEntity() {
		UserEntity returnedUser = userRepo.findByUsername(user.getUsername()).get();
		MessageEntity returnedMessage = messageRepo.findByMessageContaining("Jun");
		List<MessageRecipientEntity> messageRecipients = messageRecipientRepo.findAll();
		
		assertThat(messageRecipients).hasSize(1).allMatch(info -> info.getUserId() == 1 && info.getMessageId() == 1);
		assertThat(returnedUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
		assertThat(returnedMessage).usingRecursiveComparison().ignoringFields("id").isEqualTo(message);
	}
	
}
