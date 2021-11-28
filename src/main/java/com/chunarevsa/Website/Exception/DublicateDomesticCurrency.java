package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class DublicateDomesticCurrency extends AllException {

	public DublicateDomesticCurrency() {
		this.clientMessage = "Такая валюта уже существует";
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
	
	public DublicateDomesticCurrency(HttpStatus httpStatus) {
		this.clientMessage = "Такая валюта уже существует";
		this.httpStatus = httpStatus;
	}
}
