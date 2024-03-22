package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.AbsenceOrganizationType;

public interface AbsenceOrganizationTypeRepository extends JpaRepository<AbsenceOrganizationType, Integer> {
	
}
