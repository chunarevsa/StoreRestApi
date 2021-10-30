package com.chunarevsa.Website.service;

import java.util.*;

import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.Status;
import com.chunarevsa.Website.repo.RoleRepository;
import com.chunarevsa.Website.repo.UserRepository;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


// Добавить обработку исключений (доделать)
@Service
@Slf4j
public class UserService implements UserServiceInterface{

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(
				UserRepository userRepository, 
				RoleRepository roleRepository,
				BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User register(User user) {
		// Получаю роль (поумолчанию USER)
		Role roleUser = roleRepository.findByRole("ROLE_USER");
		// Создаю списко ролей, добавляю в него стандартное значю
		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleUser);
		// Кодирование пароля для хранения в БД
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);

		// Активация пользователя - добавить (доделать)
		user.setStatus(Status.ACTIVE);
		User registeredUser = userRepository.save(user);
		
		log.info("IN register - user: {} seccesfully registred", registeredUser);
		return registeredUser;
	}

	@Override
	public List<User> getAll() {
		List<User> result = userRepository.findAll();
		log.info("IN getAll - {} users found", result.size());
		return result;
	}

	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		log.info("IN findByUsername - user: {} found by username: {}", user, username);
		return user;
	}

	@Override
	public User findById(Long id) {

		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			log.warn("IN findById - no user found by id: {}", id);
			return null;
	  }

	  log.info("IN findById - user: {} found by id: {}", user);
		return user;
	}

	// Переделать на выключение (доделать)
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
		log.info("IN delete - user with id: {} successfully deleted");
	}
	
} 
