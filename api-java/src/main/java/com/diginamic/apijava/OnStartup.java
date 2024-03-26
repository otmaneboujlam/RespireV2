package com.diginamic.apijava;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.diginamic.apijava.entity.Absence;
import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Department;
import com.diginamic.apijava.entity.Groupe;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.entity.Role;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceOrganizationType;
import com.diginamic.apijava.enums.AbsenceStatus;
import com.diginamic.apijava.enums.AbsenceType;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;
import com.diginamic.apijava.repository.AbsenceRepository;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.DepartmentRepository;
import com.diginamic.apijava.repository.GroupeRepository;
import com.diginamic.apijava.repository.OrganizationRepository;
import com.diginamic.apijava.repository.RoleRepository;

@Component
public class OnStartup {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String jpaSettings;

	private GroupeRepository groupRepository;
	private AbsenceRepository absenceRepository;
	private AbsenceOrganizationRepository absenceOrganizationRepository;
	private DepartmentRepository departmentRepository;
	private OrganizationRepository organizationRepository;
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private PasswordEncoder passwordEncoder;
	
	public OnStartup(
			RoleRepository roleRepository, 
			AccountRepository accountRepository, 
			PasswordEncoder passwordEncoder, 
			OrganizationRepository organizationRepository, 
			DepartmentRepository departmentRepository,
			AbsenceOrganizationRepository absenceOrganizationRepository,
			AbsenceRepository absenceRepository,
			GroupeRepository groupRepository
			) {
		super();
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
		this.organizationRepository = organizationRepository;
		this.departmentRepository = departmentRepository;
		this.absenceOrganizationRepository = absenceOrganizationRepository;
		this.absenceRepository = absenceRepository;
		this.groupRepository = groupRepository;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void init() {
		
		// Check if roles exist if not create them
		
		if(roleRepository.findFirstByName("ROLE_USER").isEmpty()) {
			Role user = new Role();
			user.setName("ROLE_USER");
			roleRepository.save(user);
		}
		
		if(roleRepository.findFirstByName("ROLE_MANAGER").isEmpty()) {
			Role manager = new Role();
			manager.setName("ROLE_MANAGER");
			roleRepository.save(manager);
		}
		
		if(roleRepository.findFirstByName("ROLE_ADMIN").isEmpty()) {
			Role admin = new Role();
			admin.setName("ROLE_ADMIN");
			roleRepository.save(admin);
		}
		
		// End
		
		if("create".equals(jpaSettings)) {
			
			// Create an admin account
		
			Optional<Role> userOpt = roleRepository.findFirstByName("ROLE_USER");
			Optional<Role> managerOpt = roleRepository.findFirstByName("ROLE_MANAGER");
			Optional<Role> adminOpt = roleRepository.findFirstByName("ROLE_ADMIN");
			
			Account adminAccount = new Account();
			adminAccount.setEmail("admin@respire.com");
			adminAccount.setFirstname("admin");
			adminAccount.setLastname("admin");
			adminAccount.setPassword(passwordEncoder.encode("admin"));
			adminAccount.setEmployeeRtt(6);
			adminAccount.setPaidHoliday(25F);
			adminAccount.setStartDate(LocalDate.of(2024, 03, 28));
			adminAccount.getRoles().add(userOpt.get());
			adminAccount.getRoles().add(managerOpt.get());
			adminAccount.getRoles().add(adminOpt.get());
			accountRepository.save(adminAccount);
			
			// End
			
			// Test data
					
			Organization diginamicOrganization = new Organization();
			diginamicOrganization.setName("Diginamic");
			diginamicOrganization.setPublicHoliday(11);
			diginamicOrganization.setEmployerRtt(5);
			organizationRepository.save(diginamicOrganization);
			
			AbsenceOrganization rtt1 = new AbsenceOrganization();
			rtt1.setDate(LocalDate.of(2024, 03, 24));
			rtt1.setReason("RTT de test");
			rtt1.setOrganization(diginamicOrganization);
			rtt1.setAbsenceOrganizationType(AbsenceOrganizationType.RTT_EMPLOYEUR);
			rtt1.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.INITIALE);
			absenceOrganizationRepository.save(rtt1);
					
			Department devDepartment = new Department();
			devDepartment.setName("Recherche & Développement");
			devDepartment.setOrganization(diginamicOrganization);
			departmentRepository.save(devDepartment);
			
			Groupe managerGroup = new Groupe();
			managerGroup.setName("Manager group");
			managerGroup.setDepartment(devDepartment);
			groupRepository.save(managerGroup);
			
			Account managerAccount = new Account();
			managerAccount.setEmail("manager@respire.com");
			managerAccount.setFirstname("manager");
			managerAccount.setLastname("manager");
			managerAccount.setPassword(passwordEncoder.encode("manager"));
			managerAccount.setEmployeeRtt(6);
			managerAccount.setPaidHoliday(25F);
			managerAccount.setStartDate(LocalDate.of(2024, 03, 28));
			managerAccount.setGroupe(managerGroup);
			managerAccount.getRoles().add(userOpt.get());
			managerAccount.getRoles().add(managerOpt.get());
			accountRepository.save(managerAccount);
			
			Account managerAccount1 = new Account();
			managerAccount1.setEmail("manager1@respire.com");
			managerAccount1.setFirstname("manager1");
			managerAccount1.setLastname("manager1");
			managerAccount1.setPassword(passwordEncoder.encode("manager1"));
			managerAccount1.setEmployeeRtt(6);
			managerAccount1.setPaidHoliday(25F);
			managerAccount1.setStartDate(LocalDate.of(2024, 03, 28));
			managerAccount1.getRoles().add(userOpt.get());
			managerAccount1.getRoles().add(managerOpt.get());
			accountRepository.save(managerAccount1);
			
			Groupe recherche = new Groupe();
			recherche.setName("Recherche");
			recherche.setDepartment(devDepartment);
			recherche.setOwner(managerAccount);
			groupRepository.save(recherche);
			
			Account userAccount = new Account();
			userAccount.setEmail("user@respire.com");
			userAccount.setFirstname("user");
			userAccount.setLastname("user");
			userAccount.setPassword(passwordEncoder.encode("user"));
			userAccount.setGroupe(recherche);
			userAccount.setEmployeeRtt(6);
			userAccount.setPaidHoliday(25F);
			userAccount.setStartDate(LocalDate.of(2024, 03, 28));
			userAccount.getRoles().add(userOpt.get());
			accountRepository.save(userAccount);
			
			Account userAccount1 = new Account();
			userAccount1.setEmail("user1@respire.com");
			userAccount1.setFirstname("user1");
			userAccount1.setLastname("user1");
			userAccount1.setPassword(passwordEncoder.encode("user1"));
			userAccount1.setGroupe(recherche);
			userAccount1.setEmployeeRtt(6);
			userAccount1.setPaidHoliday(25F);
			userAccount1.setStartDate(LocalDate.of(2024, 03, 20));
			userAccount1.getRoles().add(userOpt.get());
			accountRepository.save(userAccount1);
			
			Absence cp1 = new Absence();
			cp1.setStartDate(LocalDate.of(2024, 03, 25));
			cp1.setEndDate(LocalDate.of(2024, 03, 28));
			cp1.setReason("Congé payé de test");
			cp1.setAbsenceType(AbsenceType.CONGE_PAYE);
			cp1.setAbsenceStatus(AbsenceStatus.INITIALE);
			cp1.setAccount(userAccount);
			absenceRepository.save(cp1);
			
			Absence cp2 = new Absence();
			cp2.setStartDate(LocalDate.of(2024, 03, 25));
			cp2.setEndDate(LocalDate.of(2024, 03, 28));
			cp2.setReason("Congé payé de test");
			cp2.setAbsenceType(AbsenceType.CONGE_PAYE);
			cp2.setAbsenceStatus(AbsenceStatus.INITIALE);
			cp2.setAccount(userAccount1);
			absenceRepository.save(cp2);
			
			// End test data
			
		}
		
	}
	
}
