package com.chunarevsa.website1.repo;

import com.chunarevsa.website1.entity1.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
}
