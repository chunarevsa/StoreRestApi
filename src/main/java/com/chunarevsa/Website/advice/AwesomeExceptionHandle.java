package com.chunarevsa.Website.advice;

import com.chunarevsa.Website.dto.AwesomeException;
import com.chunarevsa.Website.Exception.NotFoundItems;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandle extends ResponseEntityExceptionHandler {
	@ExceptionHandler(NotFoundItems.class)
	protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
		return new ResponseEntity<>(new AwesomeException(404, "Item not found"), HttpStatus.NOT_FOUND);
  }
}
