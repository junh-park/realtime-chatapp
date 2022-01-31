package com.jun.chatapp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.util.JsonPathExpectationsHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.chatapp.domain.dto.MessageDto;
import com.jun.chatapp.service.MessageService;

public class MessageControllerTest {
	
	private TestMessageChannel clientOutboundChannel;
	private TestAnnotationMethodHandler annotationMethodHandler;
	private MessageService messageService;
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		messageService = new TestMessageService();
		MessageController controller = new MessageController(messageService);
		
		clientOutboundChannel = new TestMessageChannel();
		
		annotationMethodHandler = new TestAnnotationMethodHandler(
				new TestMessageChannel(), clientOutboundChannel, new SimpMessagingTemplate(new TestMessageChannel()));
		
		annotationMethodHandler.registerHandler(controller);
		annotationMethodHandler.setDestinationPrefixes(List.of("/app"));
		annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
		annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
		annotationMethodHandler.afterPropertiesSet();
	}
	
	@Test
	public void sendMessageAsStringWithHeaderWithUser_And_GetItFromInboundChannel() {
		StompHeaderAccessor header = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
		header.setSubscriptionId("0");
		header.setDestination("/app/message");
		header.setSessionId("0");
		header.setUser(new TestPrincipal(1, "junpark"));
		header.setSessionAttributes(new HashMap<>());
		Message<String> message = MessageBuilder.withPayload("hello").setHeaders(header).build();
		
		annotationMethodHandler.handleMessage(message);
		
		assertThat(this.clientOutboundChannel.getMessages().size()).isEqualTo(1);
		Message<?> reply = this.clientOutboundChannel.getMessages().get(0);
		
		StompHeaderAccessor replyHeader = StompHeaderAccessor.wrap(reply);
		assertThat(replyHeader.getSessionId()).isEqualTo("0");
		assertThat(replyHeader.getSubscriptionId()).isEqualTo("0");
		assertThat(replyHeader.getDestination()).isEqualTo("/app/message");
		
		String json = new String((byte[]) reply.getPayload(), Charset.forName("UTF-8"));
		new JsonPathExpectationsHelper("$").assertValue(json, "Hello junpark");
	}
	
	@Test
	public void shouldSaveMessageToDbAndPrintTheResult_whenMessageIsSent() throws JsonMappingException, JsonProcessingException {
		String messageToSend = mapper.writeValueAsString(new MessageDto("junpark", "hello I am jun park"));
		
		StompHeaderAccessor header = StompHeaderAccessor.create(StompCommand.SEND);
		header.setDestination("/app/chat");
		header.setSessionId("0");
		header.setUser(new TestPrincipal(1, "junpark"));
		header.setSessionAttributes(new HashMap<>());
		Message<String> message = MessageBuilder.withPayload(messageToSend).setHeaders(header).build();
		
		annotationMethodHandler.handleMessage(message);
		
		assertThat(this.clientOutboundChannel.getMessages().size()).isEqualTo(1);
		Message<?> reply = this.clientOutboundChannel.getMessages().get(0);
		String payload = (String) reply.getPayload();
		MessageDto returnedMessage = mapper.readValue(payload, MessageDto.class);
		assertThat(returnedMessage).usingRecursiveComparison().isEqualTo(messageToSend);
	}
	
	private static class TestAnnotationMethodHandler extends SimpAnnotationMethodMessageHandler {

		public TestAnnotationMethodHandler(SubscribableChannel inChannel, MessageChannel outChannel,
				SimpMessageSendingOperations brokerTemplate) {

			super(inChannel, outChannel, brokerTemplate);
		}

		public void registerHandler(Object handler) {
			super.detectHandlerMethods(handler);
		}
	}

}
