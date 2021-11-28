package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.Price;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long>{
	
}
