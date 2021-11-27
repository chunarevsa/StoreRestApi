package com.chunarevsa.Website.controllers;

import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.dto.UserDto;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

	@GetMapping("/{id}") // - доделать 
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity getUserProfile (
							@AuthenticationPrincipal JwtUser jwtUser,
							@PathVariable(value = "id") Long id) {
		
		User user = userService.findById(id); // 
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		UserDto userDto = UserDto.fromUser(user);
		return new ResponseEntity<>(userDto , HttpStatus.OK);
		
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getMyProfile (@AuthenticationPrincipal JwtUser jwtUser) {
		return ResponseEntity.ok(
			"My username :" + jwtUser.getUsername() + "\n" +
			"My email :" + jwtUser.getEmail() + "\n" +
			"My roles :" +  
			jwtUser.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toSet()) + "\n" +
			"My avatar :" + jwtUser.getAvatar() +  "\n" +
			"It's all");
	}
	/*

	@AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email

	/me (USER) - Получение информаци по своим данным

	/{id} (USER) - Получение информации о пользоавтеле (переход на его профиль) 

	/allUsers (ADMIN) - Получение списка всех подтверждённых пользователей


	// old
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<UserDto> getUserById(@PathVariable (name = "id") Long id) {
		
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		UserDto userDto = UserDto.fromUser(user);
		return new ResponseEntity<>(userDto , HttpStatus.OK);
	}

	*/
	
}

