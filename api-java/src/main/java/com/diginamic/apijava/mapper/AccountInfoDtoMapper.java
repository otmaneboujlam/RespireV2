package com.diginamic.apijava.mapper;

import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AccountInfoDto;
import com.diginamic.apijava.entity.Account;

@Service
public class AccountInfoDtoMapper {

	public AccountInfoDto toDto(Account account) {
		
		AccountInfoDto dto = new AccountInfoDto(
				account.getId(),
				account.getFirstname(), 
				account.getLastname(), 
				account.getEmail(),
				account.getImage(),
				account.getRoles().stream().map(role -> role.getName()).toList()
				);
		
		return dto;
		
	}
	
}
