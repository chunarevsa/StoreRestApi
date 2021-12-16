package com.chunarevsa.website1.exception1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAmountFormat extends RuntimeException {
	
	private final String title;
   private final String fieldName;
	private final Object fieldValue;

	public InvalidAmountFormat(String title, String fieldName, Object fieldValue) {
		super(String.format("%s для ресурса %s указана не верно :'%s'", title, fieldName, fieldValue));
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
