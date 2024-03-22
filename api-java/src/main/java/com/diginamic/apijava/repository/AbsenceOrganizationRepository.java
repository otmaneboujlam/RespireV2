package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.AbsenceOrganization;

public interface AbsenceOrganizationRepository extends JpaRepository<AbsenceOrganization, Integer> {
	
}
