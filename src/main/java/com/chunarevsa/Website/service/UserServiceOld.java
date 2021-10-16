package com.chunarevsa.Website.service;
/*
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.UserModel;
import com.chunarevsa.Website.repo.UserRepository;
import com.chunarevsa.Website.service.valid.UserValid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceOld {
	
	private final UserRepository userRepository;
	private final UserValid userValid;


	public UserServiceOld(
				UserRepository userRepository,
				UserValid userValid) {
		this.userRepository = userRepository;
		this.userValid = userValid;
	}

	// Создание 
	public User addItem(User userBody) throws NotFound, FormIsEmpty {

		// Проверка на незаполеннные данные
		userValid.bodyIsEmpty(userBody);
		// Включение (active = true) 
		// userBody.setActive(true);
		return userRepository.save(userBody);
	}

	public User getUser (Long id) throws NotFound {

		// Проверка на наличие 
		if (!userValid.userIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Выводим только в случае active = true
		if (!userValid.userIsActive(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}  
		return userRepository.findById(id).orElseThrow();
	}

	// Получение модели
	public UserModel getItemModel(Long id) {
		return UserModel.toModel(userRepository.findById(id).get());
	}

	// Перезапись параметров
	public User overridUser (long id, User userBody) throws NotFound, FormIsEmpty {
		// Проверка на наличие 
		if (!userValid.userIsPresent(id)) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		} 
		// Проверка на незаполеннные данные
		 if (userValid.bodyIsEmpty(userBody)) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}  

		User user = userRepository.findById(id).orElseThrow();
		user.setUsername(userBody.getUsername());
		user.setPassword(userBody.getPassword());
		user.setAvatar(userBody.getAvatar());
		user.setCreated(userBody.getCreated());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		// user.setActive(userBody.isActive());
		// Запись параметров
		userRepository.save(user);
		return user;
	}

	public Long deleteUser(long id) throws NotFound {
		// Проверка на наличие 
		userValid.userIsPresent(id);
		// Проверка не выключен ли active = true
		// userValid.userIsActive(id);
		User user = userRepository.findById(id).orElseThrow();
		// user.setActive(false);
		userRepository.save(user);
		return id;
	}
	
} */
