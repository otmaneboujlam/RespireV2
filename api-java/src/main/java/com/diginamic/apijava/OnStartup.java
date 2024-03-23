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
import com.diginamic.apijava.entity.Contract;
import com.diginamic.apijava.entity.ContractOrganization;
import com.diginamic.apijava.entity.Department;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.entity.Role;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceOrganizationType;
import com.diginamic.apijava.enums.AbsenceStatus;
import com.diginamic.apijava.enums.AbsenceType;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;
import com.diginamic.apijava.repository.AbsenceRepository;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.ContractOrganizationRepository;
import com.diginamic.apijava.repository.ContractRepository;
import com.diginamic.apijava.repository.DepartmentRepository;
import com.diginamic.apijava.repository.OrganizationRepository;
import com.diginamic.apijava.repository.RoleRepository;

@Component
public class OnStartup {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String jpaSettings;

	private AbsenceRepository absenceRepository;
	private AbsenceOrganizationRepository absenceOrganizationRepository;
	private DepartmentRepository departmentRepository;
	private ContractRepository contractRepository;
	private OrganizationRepository organizationRepository;
	private ContractOrganizationRepository contractOrganizationRepository;
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private PasswordEncoder passwordEncoder;
	
	public OnStartup(
			RoleRepository roleRepository, 
			AccountRepository accountRepository, 
			PasswordEncoder passwordEncoder, 
			OrganizationRepository organizationRepository, 
			ContractOrganizationRepository contractOrganizationRepository, 
			ContractRepository contractRepository, 
			DepartmentRepository departmentRepository,
			AbsenceOrganizationRepository absenceOrganizationRepository,
			AbsenceRepository absenceRepository
			) {
		super();
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
		this.organizationRepository = organizationRepository;
		this.contractOrganizationRepository = contractOrganizationRepository;
		this.contractRepository = contractRepository;
		this.departmentRepository = departmentRepository;
		this.absenceOrganizationRepository = absenceOrganizationRepository;
		this.absenceRepository = absenceRepository;
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
			adminAccount.getRoles().add(userOpt.get());
			adminAccount.getRoles().add(managerOpt.get());
			adminAccount.getRoles().add(adminOpt.get());
			accountRepository.save(adminAccount);
			
			// End
			
			// Test data
			
			ContractOrganization standardContractOrganization = new ContractOrganization();
			standardContractOrganization.setPublicHoliday(11);
			standardContractOrganization.setEmployerRtt(5);
			contractOrganizationRepository.save(standardContractOrganization);
			
			Organization diginamicOrganization = new Organization();
			diginamicOrganization.setName("Diginamic");
			diginamicOrganization.setContractOrganization(standardContractOrganization);
			organizationRepository.save(diginamicOrganization);
			
			AbsenceOrganization rtt1 = new AbsenceOrganization();
			rtt1.setStartDate(LocalDate.of(2024, 03, 24));
			rtt1.setEndDate(LocalDate.of(2024, 03, 24));
			rtt1.setReason("RTT de test");
			rtt1.setOrganization(diginamicOrganization);
			rtt1.setAbsenceOrganizationType(AbsenceOrganizationType.RTT_EMPLOYER);
			rtt1.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.INITIALE);
			absenceOrganizationRepository.save(rtt1);
			
			Contract standardContract = new Contract();
			standardContract.setEmployeeRtt(6);
			standardContract.setPaidHoliday(25F);
			contractRepository.save(standardContract);
			
			Department devDepartment = new Department();
			devDepartment.setName("Recherche & Développement");
			departmentRepository.save(devDepartment);
			
			Account managerAccount = new Account();
			managerAccount.setEmail("manager@respire.com");
			managerAccount.setFirstname("manager");
			managerAccount.setLastname("manager");
			managerAccount.setPassword(passwordEncoder.encode("manager"));
			managerAccount.setOrganization(diginamicOrganization);
			managerAccount.setContract(standardContract);
			managerAccount.getRoles().add(userOpt.get());
			managerAccount.getRoles().add(managerOpt.get());
			accountRepository.save(managerAccount);
			
			Account userAccount = new Account();
			userAccount.setEmail("user@respire.com");
			userAccount.setFirstname("user");
			userAccount.setLastname("user");
			userAccount.setPassword(passwordEncoder.encode("user"));
			userAccount.setSuperior(managerAccount);
			userAccount.setOrganization(diginamicOrganization);
			userAccount.setContract(standardContract);
			userAccount.setDepartment(devDepartment);
			userAccount.getRoles().add(userOpt.get());
			accountRepository.save(userAccount);
			
			Absence cp1 = new Absence();
			cp1.setStartDate(LocalDate.of(2024, 03, 25));
			cp1.setEndDate(LocalDate.of(2024, 03, 28));
			cp1.setReason("Congé payé de test");
			cp1.setAbsenceType(AbsenceType.CONGE_PAYE);
			cp1.setAbsenceStatusE(AbsenceStatus.INITIALE);
			cp1.setAccount(userAccount);
			absenceRepository.save(cp1);
			
			// End test data
			
		}
		
	}
	
}
