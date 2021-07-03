package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.models.Products;
import org.springframework.data.repository.CrudRepository;

public interface GamesRepository extends CrudRepository<Products, Long> {
	
}
