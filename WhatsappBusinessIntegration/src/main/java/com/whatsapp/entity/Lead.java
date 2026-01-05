package com.whatsapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name="leads")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Lead {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String phoneNumber;
	private String name;
	private String email;
	private String language;
    private String preferredLocation;
	private Double budget;
	@Column(nullable=false)
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime lastInteractionAt;
	private LocalDate visitDate;
	@PrePersist
	public void onCreate() {
	    createdAt = LocalDateTime.now();
	    lastInteractionAt = LocalDateTime.now();
	    if (status == null) {
	        status = "NEW";
	    }
	}


    @PreUpdate
    public void onUpdate() {
        lastInteractionAt = LocalDateTime.now();
    }
}
