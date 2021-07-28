package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.Items;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository <Items, Long> {
	
	// Для сортировкf и подачf по частям 
	Page <Items> findAll (Pageable pageable);	
	
}
