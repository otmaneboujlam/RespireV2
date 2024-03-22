package com.diginamic.apijava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Contract;
import com.diginamic.apijava.entity.ContractOrganization;
import com.diginamic.apijava.entity.Department;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.entity.Role;
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

	private DepartmentRepository departmentRepository;
	private ContractRepository contractRepository;
	private OrganizationRepository organizationRepository;
	private ContractOrganizationRepository contractOrganizationRepository;
	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private PasswordEncoder passwordEncoder;
	
	public OnStartup(RoleRepository roleRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder, OrganizationRepository organizationRepository, ContractOrganizationRepository contractOrganizationRepository, ContractRepository contractRepository, DepartmentRepository departmentRepository) {
		super();
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
		this.organizationRepository = organizationRepository;
		this.contractOrganizationRepository = contractOrganizationRepository;
		this.contractRepository = contractRepository;
		this.departmentRepository = departmentRepository;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void init() {
		
		if("create".equals(jpaSettings)) {
			
			Role user = new Role();
			user.setName("ROLE_USER");
			roleRepository.save(user);
			
			Role manager = new Role();
			manager.setName("ROLE_MANAGER");
			roleRepository.save(manager);
			
			Role admin = new Role();
			admin.setName("ROLE_ADMIN");
			roleRepository.save(admin);
			
			ContractOrganization standardContractOrganization = new ContractOrganization();
			standardContractOrganization.setPublicHoliday(11);
			standardContractOrganization.setEmployerRtt(5);
			contractOrganizationRepository.save(standardContractOrganization);
			
			Organization diginamicOrganization = new Organization();
			diginamicOrganization.setName("Diginamic");
			diginamicOrganization.setContractOrganization(standardContractOrganization);
			organizationRepository.save(diginamicOrganization);
			
			Contract standardContract = new Contract();
			standardContract.setEmployeeRtt(6);
			standardContract.setPaidHoliday(25F);
			contractRepository.save(standardContract);
			
			Department devDepartment = new Department();
			devDepartment.setName("Recherche & Développement");
			departmentRepository.save(devDepartment);
			
			Account adminAccount = new Account();
			adminAccount.setEmail("admin@respire.com");
			adminAccount.setFirstname("admin");
			adminAccount.setLastname("admin");
			adminAccount.setPassword(passwordEncoder.encode("admin"));
			adminAccount.getRoles().add(user);
			adminAccount.getRoles().add(manager);
			adminAccount.getRoles().add(admin);
			accountRepository.save(adminAccount);
			
			Account managerAccount = new Account();
			managerAccount.setEmail("manager@respire.com");
			managerAccount.setFirstname("manager");
			managerAccount.setLastname("manager");
			managerAccount.setPassword(passwordEncoder.encode("manager"));
			managerAccount.setOrganization(diginamicOrganization);
			managerAccount.setContract(standardContract);
			managerAccount.getRoles().add(user);
			managerAccount.getRoles().add(manager);
			accountRepository.save(managerAccount);
			
			Account userAccount = new Account();
			userAccount.setEmail("user@respire.com");
			userAccount.setFirstname("user");
			userAccount.setLastname("user");
			userAccount.setPassword(passwordEncoder.encode("user"));
			userAccount.setOrganization(diginamicOrganization);
			userAccount.setContract(standardContract);
			userAccount.setDepartment(devDepartment);
			userAccount.getRoles().add(user);
			accountRepository.save(userAccount);
			
		}
		
	}
	
}
