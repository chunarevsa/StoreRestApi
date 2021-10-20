package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.dto.AdminUserDto;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminControllerV1 {

	private final UserServiceInterface userService;

	@Autowired
	public AdminControllerV1(UserServiceInterface userService) {
		this.userService = userService;
	}

	@GetMapping(value = "users/{id}")
	public ResponseEntity<AdminUserDto> getUserById(@PathVariable (name = "id") Long id) {
		
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		AdminUserDto adminUserDto = AdminUserDto.fromUser(user);
		return new ResponseEntity<>(adminUserDto , HttpStatus.OK);
	}
	
}
