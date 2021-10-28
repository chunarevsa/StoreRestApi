package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long> {
	
	//User findByUsername (String username);

	//User findByActivationCode(String code);

} 
