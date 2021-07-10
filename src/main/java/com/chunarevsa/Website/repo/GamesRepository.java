package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.models.Games;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface GamesRepository extends CrudRepository<Games, Long> {
	Page<Games> findAll (Pageable pageable);

	// Page<Games> findBynName(String name, Pageable pageable);
}
