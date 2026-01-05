package com.whatsapp.service;

import org.springframework.stereotype.Service;

import com.whatsapp.dto.AppointmentDto;
import com.whatsapp.entity.Appointment;
import com.whatsapp.entity.Lead;
import com.whatsapp.entity.Property;
import com.whatsapp.mapper.AppointmentMapper;
import com.whatsapp.repositories.AppointmentRepository;
import com.whatsapp.repositories.LeadRepository;
import com.whatsapp.repositories.PropertyRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final LeadRepository leadRepository;
    private final PropertyRepository propertyRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              LeadRepository leadRepository,
                              PropertyRepository propertyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.leadRepository = leadRepository;
        this.propertyRepository = propertyRepository;
    }

    public AppointmentDto scheduleVisit(AppointmentDto dto) {

        Lead lead = leadRepository.findById(dto.getLeadId())
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Appointment appointment =
                AppointmentMapper.toEntity(dto, lead, property);

        appointment.setStatus("REQUESTED");

        Appointment saved = appointmentRepository.save(appointment);

        return AppointmentMapper.toDTO(saved);
    }
}

