package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class InvalidFormat extends AllException {

	public InvalidFormat() {
	}
	
	public InvalidFormat(HttpStatus httpStatus) {
		this.clientMessage = "Неверный формат цены";
		this.httpStatus = httpStatus;
	}

}
