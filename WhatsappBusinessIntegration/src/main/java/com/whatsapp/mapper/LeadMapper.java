package com.whatsapp.mapper;


import com.whatsapp.dto.LeadDto;
import com.whatsapp.entity.Lead;

public class LeadMapper {

    public static LeadDto toDTO(Lead lead) {
        if (lead == null) return null;

        LeadDto dto = new LeadDto();
        dto.setId(lead.getId());
        dto.setPhoneNumber(lead.getPhoneNumber());
        dto.setName(lead.getName());
        dto.setEmail(lead.getEmail());
        dto.setLanguage(lead.getLanguage());
        dto.setPreferredLocation(lead.getPreferredLocation());
        dto.setBudget(lead.getBudget());
        dto.setStatus(lead.getStatus());
        dto.setCreatedAt(lead.getCreatedAt());
        dto.setLastInteractionAt(lead.getLastInteractionAt());

        return dto;
    }

    public static Lead toEntity(LeadDto dto) {
        if (dto == null) return null;

        Lead lead = new Lead();
        lead.setId(dto.getId());
        lead.setPhoneNumber(dto.getPhoneNumber());
        lead.setName(dto.getName());
        lead.setEmail(dto.getEmail());
        lead.setLanguage(dto.getLanguage());
        lead.setPreferredLocation(dto.getPreferredLocation());
        lead.setBudget(dto.getBudget());
        //lead.setStatus(dto.getStatus());

        return lead;
    }
}
