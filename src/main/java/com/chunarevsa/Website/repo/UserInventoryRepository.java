package com.chunarevsa.website.repo;

import com.chunarevsa.website.entity.UserInventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Long>  {
	
}
