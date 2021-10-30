package com.chunarevsa.Website.dto;

import lombok.Data;

// Запрос при аутентификации 
// Можно сделать аутентификацию по email
@Data
public class AuthRequestDto {
	
	private String username;
	private String password;
	
} 
