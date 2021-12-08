package com.chunarevsa.Website.exception;

import org.springframework.http.HttpStatus;

public class NotFound extends AllException {

	public NotFound() {}

	public NotFound(HttpStatus httpStatus) {
		this.clientMessage = "Такого элемента не существует";
		this.httpStatus = httpStatus;
	}	
	
}
