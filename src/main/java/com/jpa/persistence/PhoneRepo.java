package com.jpa.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.persistence.entities.PhoneNumber;

@Repository
public interface PhoneRepo extends JpaRepository<PhoneNumber, Long>{
}

