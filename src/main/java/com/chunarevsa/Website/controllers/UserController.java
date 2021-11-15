package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.dto.UserDto;
import com.chunarevsa.Website.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Настроить контроллеры (доступ пользователя и доступ админа)- доделать
// Контроллер для пользователя
@RestController
@RequestMapping ("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable (name = "id") Long id) {
		
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		UserDto userDto = UserDto.fromUser(user);
		return new ResponseEntity<>(userDto , HttpStatus.OK);
	}

	@GetMapping("/profile/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity getUserProfile (@PathVariable(value = "id") Long id) {
		
		return 
	}

	
}

