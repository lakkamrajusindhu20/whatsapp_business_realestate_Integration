package com.whatsapp.mapper;

import com.whatsapp.dto.PropertyDto;
import com.whatsapp.entity.Property;

public class PropertyMapper {

    public static PropertyDto toDTO(Property property) {
        if (property == null) return null;

        PropertyDto dto = new PropertyDto();
        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setDescription(property.getDescription());
        dto.setCity(property.getCity());
        dto.setArea(property.getArea());
        dto.setAddress(property.getAddress());
        dto.setPropertyType(property.getPropertyType());
        dto.setAreaSqFt(property.getAreaSqFt());
        dto.setPrice(property.getPrice());
        dto.setPriceType(property.getPriceType());
        dto.setImageUrl(property.getImageUrl());
        dto.setAvailable(property.isAvailable());

        return dto;
    }

    public static Property toEntity(PropertyDto dto) {
        if (dto == null) return null;

        Property property = new Property();
        property.setId(dto.getId());
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setCity(dto.getCity());
        property.setArea(dto.getArea());
        property.setAddress(dto.getAddress());
        property.setPropertyType(dto.getPropertyType());
        property.setAreaSqFt(dto.getAreaSqFt());
        property.setPrice(dto.getPrice());
        property.setPriceType(dto.getPriceType());
        property.setImageUrl(dto.getImageUrl());
        property.setAvailable(dto.isAvailable());

        return property;
    }
}

