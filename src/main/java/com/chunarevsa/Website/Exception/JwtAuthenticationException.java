package com.chunarevsa.Website.exception;

// 6
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException{

	public JwtAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public JwtAuthenticationException(String msg) {
		super(msg);
	}
	
} 
