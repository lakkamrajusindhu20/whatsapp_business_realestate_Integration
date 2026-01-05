package com.whatsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {

    private Long id;
    private String title;
    private String description;
    private String city;
    private String area;
    private String address;

    private String propertyType;
    private double areaSqFt;

    private double price;
    private String priceType;
    private String imageUrl;
    private boolean available;
}
