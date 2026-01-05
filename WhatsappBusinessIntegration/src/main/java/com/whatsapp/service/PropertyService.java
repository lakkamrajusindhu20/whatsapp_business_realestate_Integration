package com.whatsapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.whatsapp.dto.PropertyDto;
import com.whatsapp.entity.Property;
import com.whatsapp.mapper.PropertyMapper;
import com.whatsapp.repositories.PropertyRepository;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Admin: Add property
    public PropertyDto addProperty(PropertyDto dto) {
        Property property = PropertyMapper.toEntity(dto);
        Property saved = propertyRepository.save(property);
        return PropertyMapper.toDTO(saved);
    }

    // WhatsApp: Find properties by city & budget
    public List<PropertyDto> findProperties(String city, Double budget) {

        List<Property> properties =
                propertyRepository.findByCityIgnoreCaseAndAvailable(city, true);

        return properties.stream()
                .filter(p -> p.getPrice() <= budget)
                .map(PropertyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
