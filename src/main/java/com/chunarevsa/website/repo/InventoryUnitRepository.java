package com.chunarevsa.website.repo;

import com.chunarevsa.website.entity.InventoryUnit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryUnitRepository extends JpaRepository<InventoryUnit, Long> {
	
}
