package com.chunarevsa.Website.repo;


import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Entity.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository <Currency, Long> {
	
	// Для сортировки и подачи по частям 
		// Общий список
	Page <Currency> findAll (Pageable pageable);
		// Только active = true
	Page <Currency> findByStatus (Status status, Pageable pageable);
		// Поиск по code
	Currency findByCode (String code);

}
