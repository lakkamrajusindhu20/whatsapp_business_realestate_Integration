package com.whatsapp.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapp.entity.Property;
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

   // List<Property> findByCityAndAvailable(String city, boolean available);
	List<Property> findByCityIgnoreCaseAndAvailable(String city, boolean available);

    List<Property> findByPriceLessThanEqual(Double price);
}

