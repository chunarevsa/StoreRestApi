package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.entity.UserInventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Long>  {
	
}
