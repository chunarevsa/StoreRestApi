package com.chunarevsa.Website.repo;

import java.util.List;
import java.util.Optional;

import com.chunarevsa.Website.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername (String username);

	List<User> findByActive (Boolean acttive);

} 
