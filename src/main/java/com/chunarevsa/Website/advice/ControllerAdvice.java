package com.chunarevsa.Website.advice;

import com.chunarevsa.Website.exception.AllException;
import com.chunarevsa.Website.payload.ApiResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Обработка исключений
// TODO: логирование 
@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(ControllerAdvice.class);

	private final MessageSource messageSource;

	@Autowired
	public ControllerAdvice(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(AllException.class)
	protected ResponseEntity<ApiResponse> handleAllException(AllException allException) {
		ApiResponse response = new ApiResponse(null, null); // TODO:
		return new ResponseEntity<>(response, allException.httpStatus);
  }

} 