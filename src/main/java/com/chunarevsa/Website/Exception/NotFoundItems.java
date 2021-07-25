package com.chunarevsa.Website.Exception;

import java.util.NoSuchElementException;

public class NotFoundItems extends NoSuchElementException {

	private int code;
	private String message;

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public NotFoundItems() {
	}

	public NotFoundItems(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
