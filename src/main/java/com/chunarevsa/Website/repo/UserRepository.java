package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
	
	User findByUsername (String username);

	Boolean existsByEmail(String email);

	//User findByActivationCode(String code);

} 
