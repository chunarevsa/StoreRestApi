package com.chunarevsa.Website.Exception;

import java.util.NoSuchElementException;

public class NotFound extends NoSuchElementException {

	private String message;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotFound() {
	}
	
	public NotFound(String message) {
		this.message = message;
	}


}
