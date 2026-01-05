package com.whatsapp.service;

import org.springframework.stereotype.Service;

import com.whatsapp.dto.ConversationMessageDto;
import com.whatsapp.entity.ConversationMessage;
import com.whatsapp.entity.Lead;
import com.whatsapp.mapper.ConversationMessageMapper;
import com.whatsapp.repositories.ConversationMessageRepository;
import com.whatsapp.repositories.LeadRepository;

@Service
public class ConversationMessageService {

    private final ConversationMessageRepository messageRepository;
    private final LeadRepository leadRepository;

    public ConversationMessageService(ConversationMessageRepository messageRepository,
                                      LeadRepository leadRepository) {
        this.messageRepository = messageRepository;
        this.leadRepository = leadRepository;
    }

    public void saveMessage(ConversationMessageDto dto) {

        Lead lead = leadRepository.findById(dto.getLeadId())
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        ConversationMessage message =
                ConversationMessageMapper.toEntity(dto, lead);

        messageRepository.save(message);
    }
}
