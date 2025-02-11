package com.diginamic.apijava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diginamic.apijava.dto.AbsenceDto;
import com.diginamic.apijava.dto.AbsencePostDto;
import com.diginamic.apijava.dto.AbsencePutDto;
import com.diginamic.apijava.dto.AbsenceStatusUpdateDto;
import com.diginamic.apijava.service.AbsenceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/absence")
@EnableMethodSecurity(securedEnabled = true)
public class AbsenceController {
	
	@Autowired
	private AbsenceService absenceService;
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/all")
	public List<AbsenceDto> findAll() {
		return absenceService.findAll();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
	@GetMapping("/group")
	public List<AbsenceDto> findByGroupOwner() {
		return absenceService.findByGroupOwner(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
	@GetMapping("/group/{status}")
	public List<AbsenceDto> findByGroupOwnerAndStatus(@PathVariable("status") String status) {
		return absenceService.findByGroupOwnerAndStatus(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), status);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@GetMapping("/currentuser")
	public List<AbsenceDto> findByCurrentUser() {
		return absenceService.findByCurrentUser(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@GetMapping("/{id}")
	public AbsenceDto findOne(@PathVariable("id") Integer id) {
		return absenceService.findById(id, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@PostMapping
	public AbsenceDto createAbsence(@RequestBody @Valid AbsencePostDto absenceToCreate) {
		return absenceService.create(absenceToCreate, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@DeleteMapping("/{id}")
	public void deleteOne(@PathVariable("id") Integer id) {
		absenceService.deleteById(id, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
	@PutMapping("/process")
	public AbsenceDto updateAbsenceStatus(@RequestBody @Valid AbsenceStatusUpdateDto updatedAbsenceStatus) {
		return absenceService.updateStatus(updatedAbsenceStatus, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
	@PutMapping
	public AbsenceDto updateAbsence(@RequestBody @Valid AbsencePutDto updatedAbsence) {
		return absenceService.update(updatedAbsence, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	}
}
