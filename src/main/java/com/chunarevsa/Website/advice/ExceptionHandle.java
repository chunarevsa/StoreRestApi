package com.chunarevsa.Website.advice;

import com.chunarevsa.Website.dto.Response;
import com.chunarevsa.Website.Exception.AllException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Обработка исключений
@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AllException.class)
	protected ResponseEntity<Response> handleAllException(AllException allException) {
		Response response = new Response(allException.getClientMessage(), allException.httpStatus);
		return new ResponseEntity<>(response, allException.httpStatus);
  }

} 