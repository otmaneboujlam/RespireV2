package com.diginamic.apijava.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.apijava.dto.AbsenceScoreDto;
import com.diginamic.apijava.service.AccountAbsenceScoreService;

@RestController
@RequestMapping("/api/currentUser/absenceScore")
@EnableMethodSecurity(securedEnabled = true)
public class AccountAbsenceScoreController {
	
	private AccountAbsenceScoreService accountAbsenceScoreService;

	public AccountAbsenceScoreController(AccountAbsenceScoreService accountAbsenceScoreService) {
		super();
		this.accountAbsenceScoreService = accountAbsenceScoreService;
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@GetMapping
	public AbsenceScoreDto findCurrentUserAbsenceScore() {
		return accountAbsenceScoreService.findCurrentUserAbsenceScore(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}

}
