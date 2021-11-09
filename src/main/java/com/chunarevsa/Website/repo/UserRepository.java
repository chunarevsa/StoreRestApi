package com.chunarevsa.Website.repo;

import java.util.Optional;

import com.chunarevsa.Website.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
	
	User findByUsername (String username);

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	//User findByActivationCode(String code);

} 
