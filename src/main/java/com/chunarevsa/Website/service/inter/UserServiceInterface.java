package com.chunarevsa.Website.service.inter;

import java.util.*;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.payload.RegistrationRequest;

public interface UserServiceInterface {
	
	// Добавить обработку ошибок - доделать
	User register(RegistrationRequest newUser); 
	List<User> getAll();
	User findByUsername(String username);
	User findById(Long id);
	void delete(Long id);

} 
