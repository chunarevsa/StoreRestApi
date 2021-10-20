package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.dto.UserDto;
import com.chunarevsa.Website.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping (value = "/users/")
public class UserController {

	private final UserServiceImpl userServiceImpl; 
	// изначально стояло просто UserService. т. е ссылка на интерфейс

	@Autowired
	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable (name = "id") Long id) {
		
		User user = userServiceImpl.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		UserDto userDto = UserDto.fromUser(user);
		return new ResponseEntity<>(userDto , HttpStatus.OK);
	}
}
/*
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.UserModel;
import com.chunarevsa.Website.repo.UserRepository;
import com.chunarevsa.Website.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private final UserRepository userRepository;
	private final UserService userService;

	public UserController(
				UserRepository userRepository,
				UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	// Получение списка всех User с ограничением страницы (10)
	@RequestMapping (path = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<User> usersFindAll (@PageableDefault(sort = { "active"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<User> pageGames = userRepository.findByActive(true, pageable);
		return pageGames;
	}

	// Получение по id
	@RequestMapping (path = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserModel getOneUser (@PathVariable(value = "id") Long id) throws AllException {
		userService.getUser(id);
		return userService.getItemModel(id);
	} 

	// Добавление 
	@PostMapping (value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public User createdUser (@RequestBody User userBody) throws AllException {
		return userService.addItem(userBody);
	} 	
				
	 // Изменение
	@PutMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User editItem (@PathVariable(value = "id") long id, @RequestBody User userBody) throws AllException {
		return userService.overridUser(id, userBody);
	} 

   // Выключение
	@DeleteMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteItem(@PathVariable(value = "id") long id) throws AllException {
		return ResponseEntity.ok().body(userService.deleteUser(id));
	}
} */
