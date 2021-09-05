package com.chunarevsa.Website.repo;


import com.chunarevsa.Website.Entity.Currency1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository <Currency1, Long> {
	
	// Для сортировки и подачи по частям 
		// Общий список
	Page <Currency1> findAll (Pageable pageable);
		// Только active = true
	Page <Currency1> findByActive (boolean active, Pageable pageable);
	// 
	Currency1 findByCode (String code);

	//Optional<Currency> findByCodOptional (String code);

}
