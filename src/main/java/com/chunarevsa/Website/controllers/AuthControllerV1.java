package com.chunarevsa.Website.controllers;

import java.util.*;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.dto.AuthRequestDto;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthControllerV1 {

	private final AuthenticationManager authManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserServiceInterface userService;

	@Autowired
	public AuthControllerV1(
					AuthenticationManager authManager, 
					JwtTokenProvider jwtTokenProvider, 
					UserServiceInterface userService) {
		this.authManager = authManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	@PostMapping("login")
	public ResponseEntity login (@RequestBody AuthRequestDto authRequestDto) {
		 try {  
			String username = authRequestDto.getUsername();
			authManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequestDto.getPassword()));
			User user = userService.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("User with username: " + username + " not found");
			}
			String token = jwtTokenProvider.createToken(username, user.getRoles());

			// Не обязательная часть
			// Выводит в консоль токен username и т
			Map<Object, Object> response = new HashMap<>();
			response.put("username", username);
			response.put("token", token);

			return ResponseEntity.ok(response);
		
		  } catch (AuthenticationException e) {
				throw new BadCredentialsException("Invalid username or password");
		}  
		
	}

}
