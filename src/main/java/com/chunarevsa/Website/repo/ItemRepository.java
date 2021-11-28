package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
}
