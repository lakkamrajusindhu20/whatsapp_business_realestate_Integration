package com.whatsapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    private Long id;

    private Long leadId;
    private Long propertyId;

    private LocalDate visitDate;
    private LocalTime visitTime;

    private String status;
}
