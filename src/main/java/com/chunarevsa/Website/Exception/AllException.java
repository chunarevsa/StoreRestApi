package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;

public class AllException extends Exception{
	
	public String clientMessage;
	public HttpStatus httpStatus;
	public AllException allException;

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getClientMessage() {
		return this.clientMessage;
	}

	public void setClientMessage(String clientMessage) {
		this.clientMessage = clientMessage;
	}
	
}
