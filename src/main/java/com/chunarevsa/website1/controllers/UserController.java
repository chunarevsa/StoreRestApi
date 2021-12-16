package com.chunarevsa.website1.controllers;

import javax.validation.Valid;

import com.chunarevsa.website1.payload.ApiResponse;
import com.chunarevsa.website1.payload.LogOutRequest;
import com.chunarevsa.website1.security.jwt.JwtUser;
import com.chunarevsa.website1.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping ("/user")
@Api(value = "User Rest API", description = "Доступно только авторизованным пользователям")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Получение свего профиля
	 * В профиле отражена информация о самом пользователе, 
	 * его балансе $ и баланс внутренних валют.
	 * @param jwtUser
	 */
	@GetMapping("/profile")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение свего профиля." + 
		" В профиле отражена информация о самом пользователе, его балансе $ и баланс внутренних валют.")
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
	@ApiOperation(value = "Получение информации о пользователе" + 
		"Доступно всем авторизованным пользователяим")
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
	@ApiOperation(value = "Получение своего инвенторя")
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
	@ApiOperation(value = "Получение списка всех пользователей")
	public ResponseEntity getAllUsers (@AuthenticationPrincipal JwtUser jwtUser) {
		
		return ResponseEntity.ok().body(userService.findAllUsersDto());
	}

	/**
	 * Logout
	 * Происходит по конкретному девайсу
	 */
	@PostMapping("/logout")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Logout")
	public ResponseEntity logout(@Valid @RequestBody LogOutRequest logOutRequestDto,
					@AuthenticationPrincipal JwtUser jwtUser) {

		userService.logout(jwtUser, logOutRequestDto);
		return ResponseEntity.ok(new ApiResponse(true, "Log out successful") );
	}

}

