package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class FormIsEmpty extends AllException {

	public FormIsEmpty() {
	}

	public FormIsEmpty(HttpStatus httpStatus) {
		this.clientMessage = "Пустое поле";
		this.httpStatus = httpStatus;
	}
	
}
