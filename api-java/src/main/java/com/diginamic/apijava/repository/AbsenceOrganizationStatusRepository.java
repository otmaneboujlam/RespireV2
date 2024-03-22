package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.AbsenceOrganizationStatus;

public interface AbsenceOrganizationStatusRepository extends JpaRepository<AbsenceOrganizationStatus, Integer> {
	
}
