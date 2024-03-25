package com.diginamic.apijava.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceOrganizationDto;
import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.exception.EntityAccessDeniedException;
import com.diginamic.apijava.mapper.AbsenceOrganizationDtoMapper;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.OrganizationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AbsenceOrganizationService {
	
	@Autowired
	private AbsenceOrganizationRepository absenceOrganizationRepository;
	
	@Autowired
	private AbsenceOrganizationDtoMapper absenceOrganizationDtoMapper;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;

	public List<AbsenceOrganizationDto> findAll() {
		return absenceOrganizationRepository.findAll().stream().map((absenceOrganization) -> absenceOrganizationDtoMapper.toDto(absenceOrganization)).toList();
	}
	
	public AbsenceOrganizationDto findById(Integer id) {
		Optional<AbsenceOrganization> absenceOrganizationOpt = absenceOrganizationRepository.findById(id);
		if(absenceOrganizationOpt.isEmpty()) {
			throw new EntityNotFoundException("Entity not found");
		}
		return absenceOrganizationDtoMapper.toDto(absenceOrganizationOpt.get());
	}
	
	public List<AbsenceOrganizationDto> findByOrganization(String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		if(currentUserOpt.get().getGroupe() == null) {
			throw new EntityNotFoundException("Current user has no group");
		}
		if(currentUserOpt.get().getGroupe().getDepartment() == null) {
			throw new EntityNotFoundException("Current user has no department");
		}
		if(currentUserOpt.get().getGroupe().getDepartment().getOrganization() == null) {
			throw new EntityNotFoundException("Current user has no organization");
		}
		Optional<Organization> organizationOpt = organizationRepository.findFirstByName(currentUserOpt.get().getGroupe().getDepartment().getOrganization().getName());
		if(organizationOpt.isEmpty()) {
			throw new EntityNotFoundException("Organization not found");
		}
		return absenceOrganizationRepository.findByOrganization(organizationOpt.get()).stream().map((absenceOrganization) -> absenceOrganizationDtoMapper.toDto(absenceOrganization)).toList();
	}
	
	public AbsenceOrganizationDto findByIdInOrganization(Integer id, String email) {
		Optional<AbsenceOrganization> absenceOrganizationOpt = absenceOrganizationRepository.findById(id);
		if(absenceOrganizationOpt.isEmpty()) {
			throw new EntityNotFoundException("Entity not found");
		}
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		if(currentUserOpt.get().getGroupe() == null) {
			throw new EntityNotFoundException("Current user has no group");
		}
		if(currentUserOpt.get().getGroupe().getDepartment() == null) {
			throw new EntityNotFoundException("Current user has no department");
		}
		if(currentUserOpt.get().getGroupe().getDepartment().getOrganization() == null) {
			throw new EntityNotFoundException("Current user has no organization");
		}
		Optional<Organization> organizationOpt = organizationRepository.findFirstByName(currentUserOpt.get().getGroupe().getDepartment().getOrganization().getName());
		if(organizationOpt.isEmpty()) {
			throw new EntityNotFoundException("Organization not found");
		}
		Boolean isAbsenceOrganizationInOrganization = absenceOrganizationRepository.findByOrganization(organizationOpt.get()).contains(absenceOrganizationOpt.get());
		if(!isAbsenceOrganizationInOrganization) {
			throw new EntityAccessDeniedException("You do not have the right to view this entity");
		}
		return absenceOrganizationDtoMapper.toDto(absenceOrganizationOpt.get());
	}
	
}
