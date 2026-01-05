package com.whatsapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadDto{

    private Long id;
    private String phoneNumber;
    private String name;
    private String email;
    private String language;
    private String preferredLocation;
    private Double budget;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastInteractionAt;
}
