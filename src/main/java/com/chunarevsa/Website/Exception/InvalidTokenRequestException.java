package com.chunarevsa.Website.exception;

public class InvalidTokenRequestException extends AllException {
	
	private final String tokenType;
	private final String token;
	private final String message;

	public InvalidTokenRequestException(
						String tokenType, 
						String token, 
						String message) {
		super(String.format("%s: [%s] token: [%s] ", message, tokenType, token));
		this.tokenType = tokenType;
		this.token = token;
		this.message = message;
	}

}
