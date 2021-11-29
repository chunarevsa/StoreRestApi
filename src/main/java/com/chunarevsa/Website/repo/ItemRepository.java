package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Page<Item> findAllByActive(boolean active, Pageable pageable);
	
}
