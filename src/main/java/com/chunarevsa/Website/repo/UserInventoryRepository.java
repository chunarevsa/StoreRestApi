package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.UserInventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Long>  {
	
}
