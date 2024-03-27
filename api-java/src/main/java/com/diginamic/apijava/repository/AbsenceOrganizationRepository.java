package com.diginamic.apijava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;

public interface AbsenceOrganizationRepository extends JpaRepository<AbsenceOrganization, Integer> {

	List<AbsenceOrganization> findByOrganization(Organization organization);
	List<AbsenceOrganization> findByAbsenceOrganizationStatus(AbsenceOrganizationStatus status);
	
}
