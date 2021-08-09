package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class DublicateCurrency extends AllException {

	public DublicateCurrency() {
		this.clientMessage = "Такая валюта уже существует";
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
	
	public DublicateCurrency(HttpStatus httpStatus) {
		this.clientMessage = "Такая валюта уже существует";
		this.httpStatus = httpStatus;
	}
}
