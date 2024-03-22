package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.Absence;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
	
}
