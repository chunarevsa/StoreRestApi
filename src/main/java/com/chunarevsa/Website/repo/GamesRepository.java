package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.models.Games;
import org.springframework.data.repository.CrudRepository;

public interface GamesRepository extends CrudRepository<Games, Long> {
	
}
