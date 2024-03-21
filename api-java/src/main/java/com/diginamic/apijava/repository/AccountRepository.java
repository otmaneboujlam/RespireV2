package com.diginamic.apijava.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diginamic.apijava.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Optional<Account> findFirstByEmail(String email);

}
