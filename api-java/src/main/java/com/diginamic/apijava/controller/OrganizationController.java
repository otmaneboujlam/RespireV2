package com.diginamic.apijava.controller;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.apijava.dto.OrganizationInfoDto;
import com.diginamic.apijava.service.OrganizationService;

@RestController
@RequestMapping("/api/organization")
@EnableMethodSecurity(securedEnabled = true)
public class OrganizationController {

	private OrganizationService organizationService;

	public OrganizationController(OrganizationService organizationService) {
		super();
		this.organizationService = organizationService;
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public List<OrganizationInfoDto> findAll() {
		return organizationService.findAll();
	}
}
