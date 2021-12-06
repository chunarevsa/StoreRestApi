package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminControllers {

	private final AdminService adminService;

	@Autowired
	public AdminControllers(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * Добавление $ пользователю
	 * @param username 
	 * @param amount
	 */
	@PostMapping("/addmoney")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity addMoneyToUser (@RequestParam String username, 
			@RequestParam String amount) {
		adminService.addMoneyToUser(username, amount);
		return ResponseEntity.ok().body("Amount of " + amount + " $ was added to " + username);
	}
	
}
