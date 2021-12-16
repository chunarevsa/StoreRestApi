package com.chunarevsa.website1.exception1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserLogoutException extends RuntimeException {
	
	private final String user;
   private final String message;

   public UserLogoutException(String user, String message) {
      super(String.format("Couldn't log out device [%s]: [%s])", user, message));
      this.user = user;
      this.message = message;
   }

	public String getUser() {
		return this.user;
	}

	public String getMessage() {
		return this.message;
	}

}
