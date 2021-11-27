package com.chunarevsa.Website.dto;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.payload.DeviceInfo;

public class LogOutRequestDto {
	
	@Valid
	private DeviceInfo deviceInfo;

	public LogOutRequestDto() {
	}

	public LogOutRequestDto(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
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
			" deviceInfo='" + getDeviceInfo() + "'" +
			"}";
	}


}
