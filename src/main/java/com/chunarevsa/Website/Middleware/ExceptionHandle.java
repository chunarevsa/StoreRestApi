package com.chunarevsa.Website.Middleware;

import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.payload.Response;

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