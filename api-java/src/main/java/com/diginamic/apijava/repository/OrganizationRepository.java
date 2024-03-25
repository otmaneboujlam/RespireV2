package com.diginamic.apijava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	Optional<Organization> findFirstByName(String name);
	
}
