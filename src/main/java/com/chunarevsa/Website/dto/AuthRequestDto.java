package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.payload.DeviceInfo;

// Запрос при аутентификации 

public class AuthRequestDto {
	
	private String username;
	private String email;
	private String password;
	private DeviceInfo deviceInfo;

	public AuthRequestDto() {}

	public AuthRequestDto(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public DeviceInfo getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	@Override
	public String toString() {
		return "{" +
			" username='" + getUsername() + "'" +
			", email='" + getEmail() + "'" +
			", password='" + getPassword() + "'" +
			", deviceInfo='" + getDeviceInfo() + "'" +
			"}";
	}


	
} 
