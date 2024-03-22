package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diginamic.apijava.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
	
}
