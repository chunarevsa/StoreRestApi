package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.Currency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository <Currency, Long> {
	
	// Для сортировкf и подачf по частям 
	Page <Currency> findAll (Pageable pageable);	
	
}
