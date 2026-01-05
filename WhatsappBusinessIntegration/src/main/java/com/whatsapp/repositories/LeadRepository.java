package com.whatsapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapp.entity.Lead;
@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    Optional<Lead> findByPhoneNumber(String phoneNumber);
}

