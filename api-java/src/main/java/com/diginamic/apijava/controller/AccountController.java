package com.diginamic.apijava.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.apijava.dto.AccountInfoDto;
import com.diginamic.apijava.service.AccountService;

@RestController
@RequestMapping("/api")
@EnableMethodSecurity(securedEnabled = true)
public class AccountController {
	
	private AccountService accountService;

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	
	@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@GetMapping("/currentuser")
	public AccountInfoDto findCurrentUser() {
		return accountService.findCurrentUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}

}
