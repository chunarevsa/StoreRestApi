package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.models.Currencies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface СurrenciesRepository extends CrudRepository<Currencies, Long> {
	Page<Currencies> findAll (Pageable pageable);
}
