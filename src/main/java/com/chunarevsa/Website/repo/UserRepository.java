package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long> {
	
	User findByUsername (String username);

	Page <User> findByActive (boolean active, Pageable pageable);
	
}
