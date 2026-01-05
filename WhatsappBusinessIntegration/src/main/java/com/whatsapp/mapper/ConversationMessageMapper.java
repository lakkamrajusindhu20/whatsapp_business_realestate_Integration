package com.whatsapp.mapper;

import com.whatsapp.dto.ConversationMessageDto;
import com.whatsapp.entity.ConversationMessage;
import com.whatsapp.entity.Lead;

public class ConversationMessageMapper {

    public static ConversationMessageDto toDTO(ConversationMessage message) {
        if (message == null) return null;

        ConversationMessageDto dto = new ConversationMessageDto();
        dto.setId(message.getId());
        //dto.setLeadId(message.getLead().getId());
        if (message.getLead() != null) {
            dto.setLeadId(message.getLead().getId());
        }

        dto.setSender(message.getSender());
        dto.setMessage(message.getMessage());
        dto.setTimestamp(message.getTimestamp());

        return dto;
    }

    public static ConversationMessage toEntity(ConversationMessageDto dto, Lead lead) {
        if (dto == null) return null;

        ConversationMessage message = new ConversationMessage();
        message.setId(dto.getId());
        message.setLead(lead);
        message.setSender(dto.getSender());
        message.setMessage(dto.getMessage());
        message.setTimestamp(dto.getTimestamp());

        return message;
    }
}

