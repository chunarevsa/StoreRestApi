package com.chunarevsa.Website.repo;

import java.util.Optional;

import com.chunarevsa.Website.Entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByCurrencyTitle(String currencyTitle);
	
}
