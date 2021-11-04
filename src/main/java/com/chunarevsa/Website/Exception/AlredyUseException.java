package com.chunarevsa.Website.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus (HttpStatus.CONFLICT)
public class AlredyUseException extends RuntimeException {

	private final String title;


	public AlredyUseException(String title) {
		super(String.format("%s is alredy use", title));
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
	
}
