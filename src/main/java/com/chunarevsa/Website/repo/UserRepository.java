package com.chunarevsa.Website.repo;

import java.util.*;

import com.chunarevsa.Website.Entity.User;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long> {
	
	User findByUsername (String username);

	List<User> findAll ();

	/*
	Page <User> findAll (Pageable pageable);

	Page <User> findByActive (boolean active, Pageable pageable); */
	
} 
