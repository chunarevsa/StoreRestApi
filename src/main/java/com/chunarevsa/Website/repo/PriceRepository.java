package com.chunarevsa.Website.repo;

import java.util.Set;

import com.chunarevsa.Website.Entity.Price;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long>{
	
	//Set<Price> saveAllPricies (Set<Price> setPricies);
}
