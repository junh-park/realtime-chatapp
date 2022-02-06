package com.jun.chatapp.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.jun.chatapp.domain.dto.MessageDto;
import com.jun.chatapp.domain.entity.MessageEntity;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {
	public abstract MessageDto toMessageDto(MessageEntity message);
		
	public abstract List<MessageDto> toMessageDtoList(List<MessageEntity> messages);
	
	@Mapping(target = "recipients", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "sender", ignore = true)
	public abstract MessageEntity toMessage(MessageDto message);
}
