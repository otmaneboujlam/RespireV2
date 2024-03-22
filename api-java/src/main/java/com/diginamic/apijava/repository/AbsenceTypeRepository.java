package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.AbsenceType;

public interface AbsenceTypeRepository extends JpaRepository<AbsenceType, Integer> {
	
}
