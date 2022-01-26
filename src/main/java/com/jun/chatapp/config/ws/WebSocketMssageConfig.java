package com.jun.chatapp.config.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketMssageConfig implements WebSocketMessageBrokerConfigurer {

	private final JwtHandShakeInterceptor jwtHandshakeInterceptor;
	
	//creates in-memory message broker with one+ destination for sending and receiving messages
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic", "/queue");
	}
	
	//used to filter destination handled by methods annotated with @MessageMapping which you implement in controller
	//after processing the message, the controller will send it to the broker
	//"/mychatapplication" is the HTTP URL for the endpoint to which a WebSocket(or SockJs) client will need to connect to
	//for the WebSocket handshake
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/mychatapplication").addInterceptors(jwtHandshakeInterceptor).setAllowedOrigins("*").withSockJS();
	}
}
