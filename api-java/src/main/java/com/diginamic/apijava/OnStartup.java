package com.diginamic.apijava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Role;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.RoleRepository;

@Component
public class OnStartup {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String jpaSettings;

	private RoleRepository roleRepository;
	private AccountRepository accountRepository;
	private PasswordEncoder passwordEncoder;
	
	public OnStartup(RoleRepository roleRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		super();
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
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
			managerAccount.getRoles().add(user);
			managerAccount.getRoles().add(manager);
			accountRepository.save(managerAccount);
			
			Account userAccount = new Account();
			userAccount.setEmail("user@respire.com");
			userAccount.setFirstname("user");
			userAccount.setLastname("user");
			userAccount.setPassword(passwordEncoder.encode("user"));
			userAccount.getRoles().add(user);
			accountRepository.save(userAccount);
			
		}
		
	}
	
}
