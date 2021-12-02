package com.chunarevsa.Website.repo;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.Entity.DomesticCurrency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomesticCurrencyRepository extends JpaRepository<DomesticCurrency, Long> {
	
	Optional<DomesticCurrency> findByTitle(String title);

	Set<DomesticCurrency> findAllByActive(boolean active);

	Page<DomesticCurrency> findAll (Pageable pageable);
}
