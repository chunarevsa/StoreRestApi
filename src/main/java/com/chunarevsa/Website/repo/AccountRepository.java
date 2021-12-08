package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
}
