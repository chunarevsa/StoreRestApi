package com.chunarevsa.Website.repo;


import com.chunarevsa.Website.Entity.Currency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository <Currency, Long> {
	
	// Для сортировки и подачи по частям 
		// Общий список
	Page <Currency> findAll (Pageable pageable);
		// Только active = true
	Page <Currency> findByActive (boolean active, Pageable pageable);
	// 
	Currency findByCode (String code);

	//Optional<Currency> findByCodOptional (String code);

}
