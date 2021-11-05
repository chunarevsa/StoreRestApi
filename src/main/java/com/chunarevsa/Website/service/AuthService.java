package com.chunarevsa.Website.service;

import java.util.*;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.payload.RegistrationRequest;
import com.chunarevsa.Website.Exception.AlredyUseException;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;

public class AuthService { // добавить логи - доделать

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public AuthService(
					UserService userService, 
					JwtTokenProvider jwtTokenProvider) {
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public Optional<User> registrationUser (RegistrationRequest registrationRequest) {
		
		String email = registrationRequest.getEmail();
		if (emailAlreadyExists(email)) {
			// logger.error("Email already exists: " + newRegistrationRequestEmail);
			throw new AlredyUseException("Email");
	  }
	  User newUser = userService.addNewUser(registrationRequest);
	  User savedNewUser = userService.save(newUser);
	  return Optional.ofNullable(savedNewUser);
	}

	// Закинуть в валидацию - доделать
	private boolean emailAlreadyExists(String userEmail) {
		return userService.existsByEmail(userEmail);
	}

}
