package com.chunarevsa.Website.payload;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	
	private final String data;
	private final Boolean success;
	private final String timestamp;
	private final String cause;
	private final String path;
	
	// Ответ для исключений из AllException
	public ApiResponse(Boolean success, String data, String cause, String path) {
		this.timestamp = Instant.now().toString();
		this.data = data;
		this.success = success;
		this.cause = cause;
		this.path = path;
  	}
	
	// Ответ при удалении      УДАЛИТЬ ПОТОМ
	public ApiResponse(Boolean success, String data) {
		this.timestamp = Instant.now().toString();
		this.data = data;
		this.success = success;
		this.cause = null;
		this.path = null;
  	}


	public String getData() {
		return this.data;
	}


	public Boolean isSuccess() {
		return this.success;
	}

	public Boolean getSuccess() {
		return this.success;
	}


	public String getTimestamp() {
		return this.timestamp;
	}


	public String getCause() {
		return this.cause;
	}


	public String getPath() {
		return this.path;
	}



}
