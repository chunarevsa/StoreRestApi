package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class InvalidPriceFormat extends AllException {

	public InvalidPriceFormat() {}
	
	public InvalidPriceFormat(HttpStatus httpStatus) {
		this.clientMessage = "Неверный формат цены";
		this.httpStatus = httpStatus;
	}

}
