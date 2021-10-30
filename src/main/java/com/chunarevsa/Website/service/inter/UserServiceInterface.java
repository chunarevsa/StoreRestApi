package com.chunarevsa.Website.service.inter;

import java.util.*;

import com.chunarevsa.Website.Entity.User;

public interface UserServiceInterface {
	
	// Добавить обработку ошибок - доделать
	User register(User user); 
	List<User> getAll();
	User findByUsername(String username);
	User findById(Long id);
	void delete(Long id);

} 
