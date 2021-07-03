package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.models.Products;
import org.springframework.data.repository.CrudRepository;

public interface ProductsRepository extends CrudRepository<Products, Long> {
	
}
