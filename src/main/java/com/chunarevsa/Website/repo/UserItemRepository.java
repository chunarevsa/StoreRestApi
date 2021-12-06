package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.UserItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
	
}
