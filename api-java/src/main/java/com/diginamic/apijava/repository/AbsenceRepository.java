package com.diginamic.apijava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diginamic.apijava.entity.Absence;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.enums.AbsenceStatus;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {

	List<Absence> findByAccount(Account account);
	List<Absence> findByAccountAndAbsenceStatus(Account account, AbsenceStatus status);
	
}
