package com.chunarevsa.Website.advice;

import com.chunarevsa.Website.dto.AwesomeException;
import com.chunarevsa.Website.Exception.InvalidFormat;
import com.chunarevsa.Website.Exception.NotFoundItem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandle extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NotFoundItem.class)
	protected ResponseEntity<AwesomeException> handleNotFoundItem() {
		return new ResponseEntity<>(new AwesomeException(404, "Item not found"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidFormat.class)
	protected ResponseEntity<AwesomeException> handleInvalidFormat() {
		return new ResponseEntity<>(new AwesomeException(400, "InvalidFormat"), HttpStatus.CREATED);
  }


}
