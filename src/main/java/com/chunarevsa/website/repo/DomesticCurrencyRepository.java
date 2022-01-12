package com.chunarevsa.website.repo;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.website.entity.Currency;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/* public interface DomesticCurrencyRepository extends JpaRepository<Currency, Long> {
	
	Optional<Currency> findByTitle(String title);

	Set<DomesticCurrency> findAllByActive(boolean active);

	Page<DomesticCurrency> findAll (Pageable pageable);

	Boolean existsByTitle(String title);
} */
