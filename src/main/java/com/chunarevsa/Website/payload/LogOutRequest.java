package com.chunarevsa.Website.payload;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.payload.DeviceInfo;

public class LogOutRequest {
	
	@Valid
	private DeviceInfo deviceInfo;

	public LogOutRequest() {
	}

	public LogOutRequest(DeviceInfo deviceInfo) {
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
