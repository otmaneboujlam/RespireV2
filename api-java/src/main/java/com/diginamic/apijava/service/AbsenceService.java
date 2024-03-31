package com.diginamic.apijava.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceDto;
import com.diginamic.apijava.dto.AbsencePostDto;
import com.diginamic.apijava.dto.AbsenceStatusUpdateDto;
import com.diginamic.apijava.entity.Absence;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Groupe;
import com.diginamic.apijava.entity.Role;
import com.diginamic.apijava.enums.AbsenceStatus;
import com.diginamic.apijava.enums.AbsenceType;
import com.diginamic.apijava.exception.AbsenceReasonException;
import com.diginamic.apijava.exception.AbsenceStartEndDateException;
import com.diginamic.apijava.exception.DateParseException;
import com.diginamic.apijava.exception.EntityAccessDeniedException;
import com.diginamic.apijava.exception.IllegalAbsenceTypeException;
import com.diginamic.apijava.exception.RolesHaveNotBeenCreatedException;
import com.diginamic.apijava.exception.StatusNotFoundException;
import com.diginamic.apijava.mapper.AbsenceDtoMapper;
import com.diginamic.apijava.repository.AbsenceRepository;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.GroupeRepository;
import com.diginamic.apijava.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class AbsenceService {
	
	@Autowired
	private AbsenceRepository absenceRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AbsenceDtoMapper absenceDtoMapper;
	
	@Autowired
	private GroupeRepository groupeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<AbsenceDto> findAll() {
		return absenceRepository.findAll().stream().map((absence) -> absenceDtoMapper.toDto(absence)).toList();
	}
	
	public List<AbsenceDto> findByGroupOwner(String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		Optional<Groupe> groupOwnedByCurrentUserOpt = groupeRepository.findFirstByOwner(currentUserOpt.get());
		if(groupOwnedByCurrentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user owns no group");
		}
		List<Account> accounts = groupOwnedByCurrentUserOpt.get().getAccounts();
		List<AbsenceDto> absencesGroup = new ArrayList<AbsenceDto>();
		for (Account account : accounts) {
			for (Absence absence : account.getAbsences()) {
				absencesGroup.add(absenceDtoMapper.toDto(absence));
			}
		}
		
		return absencesGroup;
	}
	
	public List<AbsenceDto> findByGroupOwnerAndStatus(String email, String status) {
		if(status.isBlank() || ( !status.equals(AbsenceStatus.INITIALE.toString()) && !status.equals(AbsenceStatus.EN_ATTENTE_VALIDATION.toString()) && !status.equals(AbsenceStatus.VALIDEE.toString()) && !status.equals(AbsenceStatus.REJETEE.toString()) ) ) {
			throw new StatusNotFoundException("Status not found");
		}
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		Optional<Groupe> groupOwnedByCurrentUserOpt = groupeRepository.findFirstByOwner(currentUserOpt.get());
		if(groupOwnedByCurrentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user owns no group");
		}
		List<Account> accounts = groupOwnedByCurrentUserOpt.get().getAccounts();
		List<AbsenceDto> absencesGroupFilterStatus = new ArrayList<AbsenceDto>();
		for (Account account : accounts) {
			for (Absence absence : account.getAbsences()) {
				if(status.equals(absence.getAbsenceStatus().toString())) {
					absencesGroupFilterStatus.add(absenceDtoMapper.toDto(absence));
				}
			}
		}
		
		return absencesGroupFilterStatus;
	}
	
	public List<AbsenceDto> findByCurrentUser(String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		
		return absenceRepository.findByAccount(currentUserOpt.get())
				.stream()
				.filter((absence) -> absence.getStartDate().getYear() >= LocalDate.now().getYear()-1)
				.map((absence) -> absenceDtoMapper.toDto(absence)).toList();
	}
	
	public AbsenceDto findById(Integer id, String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		Optional<Absence> absenceOpt = absenceRepository.findById(id);
		if(absenceOpt.isEmpty()) {
			throw new EntityNotFoundException("Entity not found");
		}
		Optional<Role> adminRoleOpt = roleRepository.findFirstByName("ROLE_ADMIN");
		Optional<Role> managerRoleOpt = roleRepository.findFirstByName("ROLE_MANAGER");
		Optional<Role> userRoleOpt = roleRepository.findFirstByName("ROLE_USER");
		if(adminRoleOpt.isEmpty() || managerRoleOpt.isEmpty() || userRoleOpt.isEmpty()) {
			throw new RolesHaveNotBeenCreatedException("Roles have not been created");
		}
		Boolean isAdmin = currentUserOpt.get().getRoles().contains(adminRoleOpt.get());
		Boolean isManagerOfUser = false;
		Boolean isUsersAbsence = false;
		
		Optional<Groupe> groupOwnedByCurrentUserOpt = groupeRepository.findFirstByOwner(currentUserOpt.get());
		if(!groupOwnedByCurrentUserOpt.isEmpty()) {
			if(groupOwnedByCurrentUserOpt.get().getId() == absenceOpt.get().getAccount().getGroupe().getId()) {
				isManagerOfUser = true;
			}
		}
		
		if(absenceOpt.get().getAccount().getId() == currentUserOpt.get().getId()) {
			isUsersAbsence = true;
		}
		
		if( !isAdmin && !isManagerOfUser && !isUsersAbsence) {
			throw new EntityAccessDeniedException("You do not have the right to view this entity");
		}
		return absenceDtoMapper.toDto(absenceOpt.get());
	}
	
	public AbsenceDto create(@Valid AbsencePostDto absenceToCreate, String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		try {
			LocalDate.parse(absenceToCreate.getStartDate());
		} catch (Exception e) {
			throw new DateParseException(absenceToCreate.getStartDate()+" could not be parsed");
		}
		try {
			LocalDate.parse(absenceToCreate.getEndDate());
		} catch (Exception e) {
			throw new DateParseException(absenceToCreate.getEndDate()+" could not be parsed");
		}
		try {
			AbsenceType.valueOf(absenceToCreate.getAbsenceType());
		} catch (Exception e) {
			throw new IllegalAbsenceTypeException(absenceToCreate.getAbsenceType()+" does not correspond to a type of absence");
		}
		if(absenceToCreate.getAbsenceType().equals(AbsenceType.CONGE_SANS_SOLDE.toString())) {
			if(absenceToCreate.getReason().isBlank()) {
				throw new AbsenceReasonException("The reason is mandatory if the type of absence is Unpaid");
			}
		}
		if(LocalDate.parse(absenceToCreate.getStartDate()).isBefore(LocalDate.now())) {
			throw new AbsenceStartEndDateException("The start date cannot be in the past");
		}
		if(absenceToCreate.getAbsenceType().equals(AbsenceType.RTT_EMPLOYEE.toString())) {
			if(!LocalDate.parse(absenceToCreate.getStartDate()).isEqual(LocalDate.parse(absenceToCreate.getEndDate()))) {
				throw new AbsenceStartEndDateException("The start date must be equal to the end date if the type of absence is RTT employee");
			}
		}
		if(LocalDate.parse(absenceToCreate.getStartDate()).isAfter(LocalDate.parse(absenceToCreate.getEndDate()))) {
			throw new AbsenceStartEndDateException("The end date must be greater than or equal to the start date");
		}
		if(LocalDate.parse(absenceToCreate.getStartDate()).getYear() != LocalDate.parse(absenceToCreate.getEndDate()).getYear()) {
			throw new AbsenceStartEndDateException("Start date and end date must be in the same year");
		}
		if(LocalDate.parse(absenceToCreate.getStartDate()).getYear() != LocalDate.now().getYear() || LocalDate.parse(absenceToCreate.getEndDate()).getYear() != LocalDate.now().getYear()) {
			throw new AbsenceStartEndDateException("The absence request must be in the current year");
		}
		Absence a = new Absence();
		a.setStartDate(LocalDate.parse(absenceToCreate.getStartDate()));
		a.setEndDate(LocalDate.parse(absenceToCreate.getEndDate()));
		a.setAbsenceStatus(AbsenceStatus.INITIALE);
		a.setAbsenceType(AbsenceType.valueOf(absenceToCreate.getAbsenceType()));
		a.setReason(absenceToCreate.getReason());
		a.setAccount(currentUserOpt.get());
		return absenceDtoMapper.toDto(absenceRepository.save(a));
	}

	public void deleteById(Integer id, String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		Optional<Absence> absenceOpt = absenceRepository.findById(id);
		if(absenceOpt.isEmpty()) {
			throw new EntityNotFoundException("Entity not found");
		}
		Optional<Role> adminRoleOpt = roleRepository.findFirstByName("ROLE_ADMIN");
		if(adminRoleOpt.isEmpty()) {
			throw new RolesHaveNotBeenCreatedException("Roles have not been created");
		}
		Boolean isAdmin = currentUserOpt.get().getRoles().contains(adminRoleOpt.get());
		Boolean isUsersAbsence = false;	
		if(absenceOpt.get().getAccount().getId() == currentUserOpt.get().getId()) {
			isUsersAbsence = true;
		}
		if( !isAdmin && !isUsersAbsence) {
			throw new EntityAccessDeniedException("You do not have the right to delete this entity");
		}
		if(absenceOpt.get().getAbsenceStatus().equals(AbsenceStatus.VALIDEE)) {
			if(absenceOpt.get().getStartDate().isBefore(LocalDate.now())) {
				if(!isAdmin) {
					throw new EntityAccessDeniedException("You do not have the right to delete this entity");
				}
			}
		}
		absenceRepository.deleteById(id);
	}
	
	public AbsenceDto updateStatus(AbsenceStatusUpdateDto updatedAbsenceStatus, String email) {
		Optional<Account> currentUserOpt = accountRepository.findFirstByEmail(email);
		if(currentUserOpt.isEmpty()) {
			throw new EntityNotFoundException("Current user not found");
		}
		Optional<Absence> absenceOpt = absenceRepository.findById(updatedAbsenceStatus.getId());
		if(absenceOpt.isEmpty()) {
			throw new EntityNotFoundException("Entity not found");
		}
		Absence absence = absenceOpt.get();
		Optional<Role> adminRoleOpt = roleRepository.findFirstByName("ROLE_ADMIN");
		Optional<Role> managerRoleOpt = roleRepository.findFirstByName("ROLE_MANAGER");
		if(adminRoleOpt.isEmpty() || managerRoleOpt.isEmpty()) {
			throw new RolesHaveNotBeenCreatedException("Roles have not been created");
		}
		Boolean isAdmin = currentUserOpt.get().getRoles().contains(adminRoleOpt.get());
		Boolean isManagerOfUser = false;	
		Optional<Groupe> groupOwnedByCurrentUserOpt = groupeRepository.findFirstByOwner(currentUserOpt.get());
		if(!groupOwnedByCurrentUserOpt.isEmpty()) {
			if(groupOwnedByCurrentUserOpt.get().getId() == absence.getAccount().getGroupe().getId()) {
				isManagerOfUser = true;
			}
		}
		if( !isAdmin && !isManagerOfUser) {
			throw new EntityAccessDeniedException("You do not have the right to process this absence");
		}
		if(!absence.getAbsenceStatus().equals(AbsenceStatus.EN_ATTENTE_VALIDATION)) {
			throw new EntityAccessDeniedException("You do not have the right to process this absence");
		}
		if(!updatedAbsenceStatus.getAbsenceStatus().equals(AbsenceStatus.VALIDEE.toString()) && !updatedAbsenceStatus.getAbsenceStatus().equals(AbsenceStatus.REJETEE.toString())) {
			throw new StatusNotFoundException("Only validated and rejected statuses are accepted");
		}
		if(updatedAbsenceStatus.getAbsenceStatus().equals(AbsenceStatus.VALIDEE.toString())) {
			absence.setAbsenceStatus(AbsenceStatus.VALIDEE);
		}
		if(updatedAbsenceStatus.getAbsenceStatus().equals(AbsenceStatus.REJETEE.toString())) {
			absence.setAbsenceStatus(AbsenceStatus.REJETEE);
		}
		
		return absenceDtoMapper.toDto(absenceRepository.save(absence));
	}
	
}
