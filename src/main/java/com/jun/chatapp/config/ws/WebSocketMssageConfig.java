package com.jun.chatapp.config.ws;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.sockjs.client.XhrTransport;

import com.jun.chatapp.config.security.JwtTokenUtil;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@RequiredArgsConstructor
@Slf4j
public class WebSocketMssageConfig implements WebSocketMessageBrokerConfigurer {
	
	private final JwtTokenUtil tokenUtil;

	//creates in-memory message broker with one+ destination for sending and receiving messages
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic", "/queue");
	}
	
	//used to filter destination handled by methods annotated with @MessageMapping which you implement in controller
	//after processing the message, the controller will send it to the broker
	//"/chatapp" is the HTTP URL for the endpoint to which a WebSocket(or SockJs) client will need to connect to
	//for the WebSocket handshake
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
					Authentication authentication = tokenUtil.getUserFromToken(accessToken);

					accessor.setUser(authentication);
				}
				return message;
			}
		});
	}
	
	
}
