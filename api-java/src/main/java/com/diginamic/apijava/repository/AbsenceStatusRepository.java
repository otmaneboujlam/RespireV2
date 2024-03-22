package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.AbsenceStatus;

public interface AbsenceStatusRepository extends JpaRepository<AbsenceStatus, Integer> {
	
}
