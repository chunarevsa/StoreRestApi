package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class FormatException extends AllException {

	public FormatException() {
	}

	public FormatException(HttpStatus httpStatus) {
		this.clientMessage = "Этот элемент был удалён";
		this.httpStatus = httpStatus;
	}

	public FormatException(HttpStatus httpStatus, boolean empty) {
		this.clientMessage = "Пустое поле";
		this.httpStatus = httpStatus;
	}
	
}
