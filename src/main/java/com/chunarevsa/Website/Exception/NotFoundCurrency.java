package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class NotFoundCurrency  extends AllException {

	public NotFoundCurrency() {
	}

	public NotFoundCurrency(HttpStatus httpStatus) {
		this.clientMessage = "Такой валюты не существует";
		this.httpStatus = httpStatus;
	}

}
