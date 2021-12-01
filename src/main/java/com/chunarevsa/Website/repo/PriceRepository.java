package com.chunarevsa.Website.repo;

import java.util.Set;

import com.chunarevsa.Website.Entity.Price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long>{

	Page<Price> findAll (Pageable pageable);

	Set<Price> findAllByActive(boolean active);

	Set<Price> findAllByItem(Long itemId, Pageable pageable);

}
