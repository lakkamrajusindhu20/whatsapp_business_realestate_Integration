package com.whatsapp.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationMessageDto {

    private Long id;
    private Long leadId;
    private String sender;
    private String message;
    private LocalDateTime timestamp;
}

