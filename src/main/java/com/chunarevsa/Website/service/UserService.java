package com.chunarevsa.Website.service;

import java.util.*;

import com.chunarevsa.Website.Entity.User;

public interface UserService {
	
	User register(User user);

	List<User> getAll();

	User findByUsername(String username);

	User findById(Long id);

	void delete(Long id);
}
