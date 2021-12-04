package com.chunarevsa.Website.repo;

import java.util.List;
import java.util.Set;

import com.chunarevsa.Website.Entity.Account;
import com.chunarevsa.Website.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByCurrencyTitle(String currencyTitle);

	//Set<Account> findAllByUser(User user);
	
	List<Account> findAllById(Long id);

	Set<Account> findAllByUser(User user);

	Set<Account> findAllByCurrencyTitle (String currencyTitle);

	//Set<Account> 
	
}
