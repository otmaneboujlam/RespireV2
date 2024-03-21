package com.diginamic.apijava.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diginamic.apijava.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findFirstByName(String name);
}
