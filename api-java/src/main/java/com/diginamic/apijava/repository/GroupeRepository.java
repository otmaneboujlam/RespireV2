package com.diginamic.apijava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Groupe;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {

	Optional<Groupe> findFirstByOwner(Account account);
	
}
