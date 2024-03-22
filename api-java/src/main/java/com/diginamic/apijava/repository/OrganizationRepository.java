package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
	
}
