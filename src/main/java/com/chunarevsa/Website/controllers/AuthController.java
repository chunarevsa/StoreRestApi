package com.chunarevsa.Website.controllers;

import java.util.*;
import javax.validation.Valid;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.payload.RegistrationRequest;
import com.chunarevsa.Website.Exception.UserRegistrationException;
import com.chunarevsa.Website.dto.AuthRequestDto;
import com.chunarevsa.Website.event.UserRegistrationComplete;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;
import com.chunarevsa.Website.service.AuthService;
import com.chunarevsa.Website.service.UserService;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

// 8
@RestController
@RequestMapping(value = "/auth/")
public class AuthController {

	private final AuthService authService;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final AuthenticationManager authManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@Autowired
	public AuthController(
					AuthService authService,
					ApplicationEventPublisher applicationEventPublisher,
					AuthenticationManager authManager, 
					JwtTokenProvider jwtTokenProvider, 
					UserService userService) {
		this.authService = authService;
		this.applicationEventPublisher = applicationEventPublisher;
		this.authManager = authManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	// Регистрация
	@PostMapping ("/register")
	public ResponseEntity registration (@Valid @RequestBody RegistrationRequest registrationRequest ) {
		
		return authService.registrationUser(registrationRequest)
					.map(user -> {
						UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/registrationConfirmation");
						UserRegistrationComplete userRegistrationComplete = new UserRegistrationComplete(user, urlBuilder);
						applicationEventPublisher.publishEvent(userRegistrationComplete);
						return ResponseEntity.ok("Пользователь зарегистрирован, проверь почту");
						// return ResponseEntity.ok(new ApiResponse(true, "User registered successfully. Check your email for verification"));
						// доделать
					}).orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Нет такого пользователя в базу"));
	}

	public ResponseEntity confirmRegistration (@RequestParam("token") String token) {

		return authService.confirmEmailRegistration(token)
					.map(user -> ResponseEntity.ok().body("Ok")).orElseThrow();
	}

	// Авторизация
	/* @PostMapping("login")
	public ResponseEntity login (@RequestBody AuthRequestDto authRequestDto) {
		 try { 
			String username = authRequestDto.getUsername();

			// Запрос аутентификации по username и password
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequestDto.getPassword()));

			// Получение пользователя
			User user = userService.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("User with username: " + username + " not found");
			}

			// Создание для пользователя токена
			String token = jwtTokenProvider.createToken(username, user.getRoles());

			// Не обязательная часть
			// Выводит в консоль токен username и т
			Map<Object, Object> response = new HashMap<>();
			response.put("username", username);
			response.put("token", token);

			// Сделать возврат об успешной авторизации без токена
			return ResponseEntity.ok(response); 
		
		  } catch (AuthenticationException e) {
			  	System.err.println("НЕВЕРНЫЙ ПАРОЛИ ИЛИ ИМЯ ПОЛЬЗОВАТЕЛЯ");
				throw new BadCredentialsException("Invalid username or password");
		}  
		
	} */

} 
