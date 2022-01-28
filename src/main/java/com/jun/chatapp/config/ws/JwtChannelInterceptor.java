package com.jun.chatapp.config.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
public class JwtChannelInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		// TODO Auto-generated method stub
		return ChannelInterceptor.super.preSend(message, channel);
	}
}
