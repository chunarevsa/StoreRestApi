package com.chunarevsa.website.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Registration Request", description = "The registration request payload")
public class RegistrationRequest {
	
	@NotBlank(message = "Registration username cannot be blank")
	@ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String")
	private String username;

	@NotNull(message = "Registration password cannot be null")
   @ApiModelProperty(value = "A valid password string", required = true, allowableValues = "NonEmpty String")
	private String password;

	@NotBlank(message = "Registration email cannot be blank")
	@ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
	private String email;

	@NotNull(message = "Whether the user has to be registered as an admin or not")
	@ApiModelProperty(value = "Указывает обладает ли пользователь правами администратора", required = true,
            dataType = "boolean", allowableValues = "true, false")
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
