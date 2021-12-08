package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.payload.ApiResponse;
import com.chunarevsa.Website.service.AdminService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
@Api(value = "Admin Rest API", description = "Функции администратора")
public class AdminControllers {

	private static final Logger logger = LogManager.getLogger(AdminControllers.class);

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
	@ApiOperation(value = "Добавление $ пользователю")
	public ResponseEntity addMoneyToUser (@RequestParam String username, 
			@RequestParam String amount) {
		adminService.addMoneyToUser(username, amount);
		logger.info("Amount of " + amount + " $ was added to " + username);
		return ResponseEntity.ok(new ApiResponse(true, "Amount of " + amount + " $ was added to " + username));
	}
	
}
