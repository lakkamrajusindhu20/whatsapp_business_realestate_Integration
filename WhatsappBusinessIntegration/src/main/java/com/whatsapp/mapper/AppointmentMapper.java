package com.whatsapp.mapper;

import com.whatsapp.dto.AppointmentDto;
import com.whatsapp.entity.Appointment;
import com.whatsapp.entity.Lead;
import com.whatsapp.entity.Property;

public class AppointmentMapper {

    public static AppointmentDto toDTO(Appointment appointment) {
        if (appointment == null) return null;

        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
       // dto.setLeadId(appointment.getLead().getId());
        //dto.setPropertyId(appointment.getProperty().getId());
        if (appointment.getLead() != null) {
            dto.setLeadId(appointment.getLead().getId());
        }
        if (appointment.getProperty() != null) {
            dto.setPropertyId(appointment.getProperty().getId());
        }

        dto.setVisitDate(appointment.getVisitDate());
        dto.setVisitTime(appointment.getVisitTime());
        dto.setStatus(appointment.getStatus());

        return dto;
    }

    public static Appointment toEntity(AppointmentDto dto, Lead lead, Property property) {
        if (dto == null) return null;

        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());
        appointment.setLead(lead);
        appointment.setProperty(property);
        appointment.setVisitDate(dto.getVisitDate());
        appointment.setVisitTime(dto.getVisitTime());
        appointment.setStatus(dto.getStatus());

        return appointment;
    }
}
