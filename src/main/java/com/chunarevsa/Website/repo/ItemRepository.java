package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository <Item, Long> {
	
	// Для сортировки и подачи по частям 
		// Общий список
	/* Page <Item> findAll (Pageable pageable);
		// Только Status.ACTIVE
	Page <Item> findByStatus (Status status, Pageable pageable); */
	
}
