package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.ContractOrganization;

public interface ContractOrganizationRepository extends JpaRepository<ContractOrganization, Integer> {
	
}
