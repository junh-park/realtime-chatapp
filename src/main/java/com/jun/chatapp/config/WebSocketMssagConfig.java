package com.jun.chatapp.config;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class WebSocketMssagConfig implements WebSocketMessageBrokerConfigurer {

	//creates in-memory message broker with one+ destination for sending and receiving messages
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic", "/queue");
	}
	
	//used to filter destination handled by methods annotated with @MessageMapping which you implement in controller
	//after processing the message, the controller will send it to the broker
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").setAllowedOriginPatterns("httplocalhost:3000").withSockJS();
	}
}
