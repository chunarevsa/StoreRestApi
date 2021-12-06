package com.chunarevsa.Website.Entity.payload;

public class RegistrationRequest {
	
	private String username;
	private String password;
	private String email;
	private Boolean registerAsAdmin;

	public RegistrationRequest() {}

	public RegistrationRequest(
					String username, 
					String password, 
					String email, 
					Boolean registerAsAdmin) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.registerAsAdmin = registerAsAdmin;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean isRegisterAsAdmin() {
		return this.registerAsAdmin;
	}

	public Boolean getRegisterAsAdmin() {
		return this.registerAsAdmin;
	}

	public void setRegisterAsAdmin(Boolean registerAsAdmin) {
		this.registerAsAdmin = registerAsAdmin;
	}

	@Override
	public String toString() {
		return "{" +
			" username='" + getUsername() + "'" +
			", password='" + getPassword() + "'" +
			", email='" + getEmail() + "'" +
			", registerAsAdmin='" + isRegisterAsAdmin() + "'" +
			"}";
	}

}
