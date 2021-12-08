package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.entity.Price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long>{

	Page<Price> findAll (Pageable pageable);

}
