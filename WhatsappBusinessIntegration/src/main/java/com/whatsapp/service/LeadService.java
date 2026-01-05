package com.whatsapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whatsapp.dto.LeadDto;
import com.whatsapp.entity.Lead;
import com.whatsapp.mapper.LeadMapper;
import com.whatsapp.repositories.LeadRepository;

@Service
public class LeadService {

    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    // Create or fetch lead by phone number
    public LeadDto getOrCreateLead(String phoneNumber, String language) {

        Optional<Lead> existingLead = leadRepository.findByPhoneNumber(phoneNumber);

        if (existingLead.isPresent()) {
            return LeadMapper.toDTO(existingLead.get());
        }

        Lead lead = new Lead();
        lead.setPhoneNumber(phoneNumber);
        lead.setLanguage(language);

        Lead savedLead = leadRepository.save(lead);

        return LeadMapper.toDTO(savedLead);
    }

    public Lead getLeadEntityByPhone(String phoneNumber) {
        return leadRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Lead not found"));
    }
}

