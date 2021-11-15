package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.payload.RegistrationRequest;
import com.chunarevsa.Website.Exception.UserLoginException;
import com.chunarevsa.Website.Exception.UserRegistrationException;
import com.chunarevsa.Website.dto.AuthRequestDto;
import com.chunarevsa.Website.event.UserRegistrationComplete;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Autowired
	public AuthController(
					AuthService authService,
					ApplicationEventPublisher applicationEventPublisher) {
		this.authService = authService;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	// Регистрация
	@PostMapping ("/register")
	public ResponseEntity registration (@Valid @RequestBody RegistrationRequest registrationRequest ) {
		
		return authService.registrationUser(registrationRequest)
					.map(user -> {
						UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/registrationConfirmation");
						UserRegistrationComplete userRegistrationComplete = new UserRegistrationComplete(user, urlBuilder);
						applicationEventPublisher.publishEvent(userRegistrationComplete);
						return ResponseEntity.ok("Для завершения регистрации перейдите по ссылке в письме");
					}).orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Нет такого пользователя в базе"));
	}

	@GetMapping("/registrationConfirmation")
	public ResponseEntity confirmRegistration (@RequestParam("token") String token) {

		return authService.confirmEmailRegistration(token)
					.map(user -> ResponseEntity.ok().body("Учётная запись подтверждена")).orElseThrow();
	}

	@PostMapping("/login")
	public ResponseEntity login (@Valid @RequestBody AuthRequestDto authRequestDto) {
		 
		Authentication authentication = authService.authenticateUser(authRequestDto)
			.orElseThrow(() -> new UserLoginException("Не удалось войти в систему - " + authRequestDto));
		
		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(authentication);  
		
		return ResponseEntity.ok().body("Успешная авторизиция");

	} 


} 
