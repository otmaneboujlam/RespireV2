package com.diginamic.apijava.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceOrganizationDto;
import com.diginamic.apijava.dto.AbsenceOrganizationPostDto;
import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceOrganizationType;
import com.diginamic.apijava.exception.AbsenceStartEndDateException;
import com.diginamic.apijava.exception.DateParseException;
import com.diginamic.apijava.exception.EntityAccessDeniedException;
import com.diginamic.apijava.exception.IllegalAbsenceTypeException;
import com.diginamic.apijava.mapper.AbsenceOrganizationDtoMapper;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.OrganizationRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

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
	
	public AbsenceOrganizationDto create(@Valid AbsenceOrganizationPostDto absenceOrganizationToCreate) {
		Optional<Organization> organizationOpt = organizationRepository.findFirstByName(absenceOrganizationToCreate.getOrganization());
		if(organizationOpt.isEmpty()) {
			throw new EntityNotFoundException("Organization not found");
		}
		try {
			LocalDate.parse(absenceOrganizationToCreate.getDate());
		} catch (Exception e) {
			throw new DateParseException(absenceOrganizationToCreate.getDate()+" could not be parsed");
		}
		try {
			AbsenceOrganizationType.valueOf(absenceOrganizationToCreate.getAbsenceOrganizationType());
		} catch (Exception e) {
			throw new IllegalAbsenceTypeException(absenceOrganizationToCreate.getAbsenceOrganizationType()+" does not correspond to a type of absence");
		}
		if(LocalDate.parse(absenceOrganizationToCreate.getDate()).isBefore(LocalDate.now())) {
			throw new AbsenceStartEndDateException("The date cannot be in the past");
		}
		if(LocalDate.parse(absenceOrganizationToCreate.getDate()).getYear() != LocalDate.now().getYear()) {
			throw new AbsenceStartEndDateException("The absence request must be in the current year");
		}
		AbsenceOrganization a = new AbsenceOrganization();
		a.setDate(LocalDate.parse(absenceOrganizationToCreate.getDate()));
		a.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.INITIALE);
		a.setAbsenceOrganizationType(AbsenceOrganizationType.valueOf(absenceOrganizationToCreate.getAbsenceOrganizationType()));
		a.setReason(absenceOrganizationToCreate.getReason());
		a.setOrganization(organizationOpt.get());
		return absenceOrganizationDtoMapper.toDto(absenceOrganizationRepository.save(a));
	}
	
}
