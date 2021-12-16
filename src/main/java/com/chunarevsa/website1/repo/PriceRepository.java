package com.chunarevsa.website1.repo;

import com.chunarevsa.website1.entity1.Price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long>{

	Page<Price> findAll (Pageable pageable);

}
