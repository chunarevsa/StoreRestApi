package com.chunarevsa.Website.service.valid;
/* 
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.repo.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserValid {
	
	private final UserRepository userRepository;

	public UserValid(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Проверка на наличие 
	public boolean userIsPresent (long id) {
		return userRepository.findById(id).isPresent(); 
	}

	// Проверка не выключен ли active = true
	public boolean userIsActive (Long id) {
		return userRepository.findById(id).orElseThrow().isActive();
	} 

	// Проверка на незаполеннные данные
	public boolean bodyIsEmpty (User userBody) throws FormIsEmpty {
		if (
			userBody.getUsername().isEmpty()  ||
			userBody.getPassword().isEmpty() || 
			userBody.getAvatar().isEmpty()) {
				return true;
		}
		return false;
	}

} */
