package com.chunarevsa.Website.Exception;

public class UserLogoutException extends RuntimeException {

	private final String user;
   private final String message;

   public UserLogoutException(String user, String message) {
      super(String.format("Couldn't log out device [%s]: [%s])", user, message));
      this.user = user;
      this.message = message;
   }
	
}
