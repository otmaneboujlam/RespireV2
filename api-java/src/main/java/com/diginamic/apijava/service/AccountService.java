package com.diginamic.apijava.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AccountInfoDto;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.mapper.AccountInfoDtoMapper;
import com.diginamic.apijava.repository.AccountRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountService {

	private AccountInfoDtoMapper accountInfoDtoMapper;
	private AccountRepository accountRepository;
	
	public AccountService(AccountInfoDtoMapper accountInfoDtoMapper, AccountRepository accountRepository) {
		super();
		this.accountInfoDtoMapper = accountInfoDtoMapper;
		this.accountRepository = accountRepository;
	}

	public AccountInfoDto findCurrentUser(String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		return accountInfoDtoMapper.toDto(currentUserOpt.get());
	}
}
