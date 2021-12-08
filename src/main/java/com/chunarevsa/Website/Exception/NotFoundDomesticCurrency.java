package com.chunarevsa.Website.exception;

import org.springframework.http.HttpStatus;

public class NotFoundDomesticCurrency  extends AllException {

	public NotFoundDomesticCurrency() {
	}

	public NotFoundDomesticCurrency(HttpStatus httpStatus) {
		this.clientMessage = "Такой валюты не существует";
		this.httpStatus = httpStatus;
	}

}
