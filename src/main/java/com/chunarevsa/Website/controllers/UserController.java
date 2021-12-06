package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.dto.LogOutRequest;
import com.chunarevsa.Website.event.UserLogoutSuccess;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
@RequestMapping ("/user")
public class UserController {

	private final UserService userService;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	public UserController(UserService userService,
			ApplicationEventPublisher applicationEventPublisher) {
		this.userService = userService;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * Получение свего профиля
	 * В профиле отражена информация о самом пользователе, 
	 * его балансе $ и баланс внутренних валют.
	 * @param jwtUser
	 */
	@GetMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getMyUserProfile (@AuthenticationPrincipal JwtUser jwtUser) {
		return ResponseEntity.ok().body(userService.getMyUserProfile(jwtUser));
	} 

	/**
	 * Получение информации о пользователе
	 * Доступно всем авторизованным пользователяим
	 * @param username
	 * @param jwtUser
	 */
	@GetMapping("/{username}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getUserProfile(@PathVariable(value = "username") String username,
					@AuthenticationPrincipal JwtUser jwtUser) {

		return ResponseEntity.ok().body(userService.getUserProfile(username));
	}

	/**
	 * Получение своего инвенторя
	 * Вся информация выдаётся через DTO
	 * @param jwtUser
	 */
	@GetMapping("/profile/inventory")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getUserInventory (@AuthenticationPrincipal JwtUser jwtUser) {
		
		return ResponseEntity.ok().body(userService.getUserInventory(jwtUser));	
	} 

	/**
	 * Получение списка всех пользователей
	 * @param jwtUser
	 * @return
	 */
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity getAllUsers (@AuthenticationPrincipal JwtUser jwtUser) {
		
		return ResponseEntity.ok(userService.findAllUsersDto());
	}

	/**
	 * Logout
	 * Происходит по конкретному девайсу
	 */
	@PostMapping("/logout")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity logout(@Valid @RequestBody LogOutRequest logOutRequestDto,
					@AuthenticationPrincipal JwtUser jwtUser) {

		userService.logout(jwtUser, logOutRequestDto);
		Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		UserLogoutSuccess logoutSuccess = new UserLogoutSuccess(
			jwtUser.getEmail(), credentials.toString(), logOutRequestDto);
		applicationEventPublisher.publishEvent(logoutSuccess);
		return ResponseEntity.ok("Log out successful");
	}

}

