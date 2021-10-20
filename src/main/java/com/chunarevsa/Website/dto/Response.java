package com.chunarevsa.Website.dto;

import org.springframework.http.HttpStatus;

public class Response {
	
	private String message;
	private String code;
	private String clientMessage;

	public String getMessage() {return this.message;}
	public void setMessage(String message) {this.message = message;}

	public String getCode() {return this.code;}
	public void setCode(String code) {this.code = code;}

	public String getClientMessage() {return this.clientMessage;}
	public void setClientMessage(String clientMessage) {this.clientMessage = clientMessage;}

	public Response() {}
	
	// Ответ для исключений из AllException
	public Response(String clientMessage, HttpStatus httpStatus) {
		HttpStatus httpStatus2 = httpStatus;
		String[] httpMessage = httpStatus2.toString().split(" ");
		this.code = httpMessage[0];
		this.message = httpStatus2.getReasonPhrase();
		this.clientMessage = clientMessage;
	}
	
	// Ответ при удалении      УДАЛИТЬ ПОТОМ
	public Response (boolean active) {
		HttpStatus httpStatus2 = HttpStatus.OK;
		String[] httpMessage = httpStatus2.toString().split(" ");
		this.code = httpMessage[0];
		this.message = httpStatus2.getReasonPhrase();
		this.clientMessage = "Успешное удаленние";
	}

}
