package com.diginamic.apijava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Organization;

public interface AbsenceOrganizationRepository extends JpaRepository<AbsenceOrganization, Integer> {

	List<AbsenceOrganization> findByOrganization(Organization organization);
	
}
