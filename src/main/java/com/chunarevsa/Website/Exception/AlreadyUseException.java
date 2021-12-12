package com.chunarevsa.Website.exception;
 
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus (HttpStatus.CONFLICT)
public class AlreadyUseException extends RuntimeException {

	private final String title;
	private final String fieldName;
	private final transient Object fieldValue;

	public AlreadyUseException(String title, String fieldName, Object fieldValue) {
		super(String.format("%s already in use with %s : '%s'", title, fieldName, fieldValue));
		this.title = title;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getTitle() {
		return this.title;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public Object getFieldValue() {
		return this.fieldValue;
	}

} 
