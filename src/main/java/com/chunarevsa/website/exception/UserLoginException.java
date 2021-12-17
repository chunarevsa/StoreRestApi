package com.chunarevsa.website.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserLoginException extends RuntimeException {

	private final String path;
   private final String email;

	public UserLoginException(String path, String email) {
      super(String.format("Не удалось войти в систему. Ошибка [%s] для [%s])", path, email));
      this.path = path;
      this.email = email;
   }

	public String getPath() {
		return this.path;
	}


	public String getEmail() {
		return this.email;
	}

}
