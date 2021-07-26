package com.chunarevsa.Website.Exception;

public class AwesomeException {
	
	private String message;
	private int code;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public AwesomeException() {
	}

	public AwesomeException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	


}
