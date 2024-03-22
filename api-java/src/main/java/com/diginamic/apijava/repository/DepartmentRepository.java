package com.diginamic.apijava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diginamic.apijava.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	
}
