package com.chunarevsa.website1.repo;

import com.chunarevsa.website1.entity1.UserInventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Long>  {
	
}
