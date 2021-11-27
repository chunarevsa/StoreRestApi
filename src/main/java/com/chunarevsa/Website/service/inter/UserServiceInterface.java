package com.chunarevsa.Website.service.inter;

import java.util.*;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.payload.RegistrationRequest;

public interface UserServiceInterface {
	
	// Добавить обработку ошибок - доделать
	User addNewUser (RegistrationRequest newUser); 
	List<User> getAll();
	Optional<User> findByUsername(String username);
	User findById(Long id);
	void delete(Long id);
	Optional<User> findByEmail(String email);

} 
