package com.chunarevsa.Website.advice;

import com.chunarevsa.Website.dto.Response;
import com.chunarevsa.Website.Exception.InvalidFormat;
import com.chunarevsa.Website.Exception.NotFound;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Обработка исключений
@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {
	
	// Not Found Item
	@ExceptionHandler(NotFound.class)
	protected ResponseEntity<Response> handleNotFoundItem() {
		return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

   // Неверный тип данных, неверная сумма, пустая срока
   @ExceptionHandler(InvalidFormat.class)
	protected ResponseEntity<Response> handleInvalidFormat() {
		return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  } 
}
