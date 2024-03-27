package com.diginamic.apijava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.apijava.service.AdminCronService;

@RestController
@RequestMapping("/api/startCron")
@EnableMethodSecurity(securedEnabled = true)
public class CronController {

	@Autowired
	private AdminCronService adminCronService;
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public void startCron() {
		adminCronService.startCron();
	}
}
