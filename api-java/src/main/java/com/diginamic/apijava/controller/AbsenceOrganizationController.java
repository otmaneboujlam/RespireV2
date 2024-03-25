package com.diginamic.apijava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.apijava.dto.AbsenceOrganizationDto;
import com.diginamic.apijava.service.AbsenceOrganizationService;

@RestController
@RequestMapping("/api/absenceOrganization")
@EnableMethodSecurity(securedEnabled = true)
public class AbsenceOrganizationController {
	
	@Autowired
	private AbsenceOrganizationService absenceOrganizationService;
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/all")
	public List<AbsenceOrganizationDto> findAll() {
		return absenceOrganizationService.findAll();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@GetMapping("/organization")
	public List<AbsenceOrganizationDto> findByOrganization() {
		return absenceOrganizationService.findByOrganization(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/{id}")
	public AbsenceOrganizationDto findById(@PathVariable("id") Integer id) {
		return absenceOrganizationService.findById(id);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@GetMapping("/organization/{id}")
	public AbsenceOrganizationDto findByIdInOrganization(@PathVariable("id") Integer id) {
		return absenceOrganizationService.findByIdInOrganization(id, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}

}
