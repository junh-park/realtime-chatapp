package com.jun.chatapp.config.ws;

import java.security.Principal;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.*;

import com.jun.chatapp.config.security.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
@Slf4j
public class WebSocketMssageConfig implements WebSocketMessageBrokerConfigurer {

	private final JwtTokenUtil tokenUtil;

	private DefaultSimpUserRegistry userRegistry = new DefaultSimpUserRegistry();
	private DefaultUserDestinationResolver resolver = new DefaultUserDestinationResolver(userRegistry);

	@Bean
	@Primary
	public SimpUserRegistry userRegistry() {
		return userRegistry;
	}

	@Bean
	@Primary
	public UserDestinationResolver userDestinationResolver() {
		return resolver;
	}

	// creates in-memory message broker with one+ destination for sending and
	// receiving messages
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic", "/queue");
	}

	// used to filter destination handled by methods annotated with @MessageMapping
	// which you implement in controller
	// after processing the message, the controller will send it to the broker
	// "/chatapp" is the HTTP URL for the endpoint to which a WebSocket(or SockJs)
	// client will need to connect to
	// for the WebSocket handshake
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chatapp").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					List<String> auth = accessor.getNativeHeader("X-Authorization");
					log.info("X-Authorization: {}", auth);

					String accessToken = auth.get(0).split(" ")[1];

					if (tokenUtil.validate(accessToken)) {
						Principal authentication = tokenUtil.getUserFromToken(accessToken);

						setSessionEvent(message, accessor, authentication);

						accessor.setUser(authentication);
						accessor.setLeaveMutable(true);
					}
				}
				return message;
			}
		});
	}
	
	private void setSessionEvent(Message<?> message, StompHeaderAccessor accessor, Principal authentication) {
		switch (accessor.getMessageType()) {
		case CONNECT:
			userRegistry.onApplicationEvent(
					new SessionConnectedEvent(this, (Message<byte[]>) message, authentication)); break;
		case SUBSCRIBE:
			userRegistry.onApplicationEvent(
					new SessionSubscribeEvent(this, (Message<byte[]>) message, authentication)); break;
		case UNSUBSCRIBE:
			userRegistry.onApplicationEvent(
					new SessionUnsubscribeEvent(this, (Message<byte[]>) message, authentication)); break;
		case DISCONNECT:
			userRegistry.onApplicationEvent(new SessionDisconnectEvent(this, (Message<byte[]>) message,
					accessor.getSessionId(), CloseStatus.NORMAL)); break;
		default: break;
		}
	}

}
