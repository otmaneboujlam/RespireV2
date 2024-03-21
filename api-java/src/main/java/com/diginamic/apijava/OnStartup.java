package com.diginamic.apijava;

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
		
		// Créer un compte de test, email = otmane.boujlam@gmail.com password = otmane
//		Account testAccount = new Account();
//		testAccount.setEmail("otmane.boujlam@gmail.com");
//		testAccount.setFirstname("Otmane");
//		testAccount.setLastname("Boujlam");
//		testAccount.setPassword(passwordEncoder.encode("otmane"));
//		accountRepository.save(testAccount);
		
				
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
		
	}
	
}
