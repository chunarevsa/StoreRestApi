package com.chunarevsa.Website.Exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException{

	public JwtAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
		//TODO Auto-generated constructor stub
	}

	public JwtAuthenticationException(String msg) {
		super(msg);
		//TODO Auto-generated constructor stub
	}
	
}
