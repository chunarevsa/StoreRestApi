package com.chunarevsa.website.repo;

import com.chunarevsa.website.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
}
