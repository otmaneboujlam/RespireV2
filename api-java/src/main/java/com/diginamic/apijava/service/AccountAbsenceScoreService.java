package com.diginamic.apijava.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceScoreDto;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.helper.AbsenceScoreHelper;
import com.diginamic.apijava.repository.AccountRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountAbsenceScoreService {

	private AccountRepository accountRepository;
	private AbsenceScoreHelper absenceScoreHelper; 
	
	public AccountAbsenceScoreService(AbsenceScoreHelper absenceScoreHelper, AccountRepository accountRepository) {
		super();
		this.absenceScoreHelper = absenceScoreHelper;
		this.accountRepository = accountRepository;
	}

	public AbsenceScoreDto findCurrentUserAbsenceScore(String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		
		return absenceScoreHelper.calculateAbsenceScore(currentUserOpt.get());
	}
}
