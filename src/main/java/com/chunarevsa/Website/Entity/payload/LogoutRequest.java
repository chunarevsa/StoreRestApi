package com.chunarevsa.Website.Entity.payload;

import javax.validation.Valid;

public class LogoutRequest {
	
	@Valid
	private DeviceInfo deviceInfo;

	public LogoutRequest() {}

	public LogoutRequest(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public DeviceInfo getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}



}
