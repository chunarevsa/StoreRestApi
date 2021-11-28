package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.Currency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
	
	Currency findByCode (String code);

}
