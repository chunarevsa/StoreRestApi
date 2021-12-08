package com.chunarevsa.Website.middleware;

import com.chunarevsa.Website.exception.AllException;
import com.chunarevsa.Website.payload.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Обработка исключений
@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AllException.class)
	protected ResponseEntity<ApiResponse> handleAllException(AllException allException) {
		ApiResponse response = new ApiResponse(null, null); // TODO:
		return new ResponseEntity<>(response, allException.httpStatus);
  }

} 