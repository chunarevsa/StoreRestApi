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

// 8
@RestController
@RequestMapping(value = "/auth/")
public class AuthController {

	private final AuthenticationManager authManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserServiceInterface userService;

	@Autowired
	public AuthController(
					AuthenticationManager authManager, 
					JwtTokenProvider jwtTokenProvider, 
					UserServiceInterface userService) {
		this.authManager = authManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	// Авторизация
	@PostMapping("login")
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
		
	}

} 
