package com.chunarevsa.Website.dto;

import org.springframework.http.HttpStatus;

public class Response {
	
	private String message, code, clientMessage;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClientMessage() {
		return this.clientMessage;
	}

	public void setClientMessage(String clientMessage) {
		this.clientMessage = clientMessage;
	}

	public Response() {
	}

	public Response(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Response(HttpStatus httpStatus) {
		HttpStatus httpStatus2 = httpStatus;
		String[] httpMessage = httpStatus2.toString().split(" ");
		this.code = httpMessage[0];
		this.message = httpStatus.getReasonPhrase();
	}

	public Response(String clientMessage, HttpStatus httpStatus) {
		HttpStatus httpStatus2 = httpStatus;
		String[] httpMessage = httpStatus2.toString().split(" ");
		this.code = httpMessage[0];
		this.message = httpStatus2.getReasonPhrase();
		this.clientMessage = clientMessage;
	}

}
