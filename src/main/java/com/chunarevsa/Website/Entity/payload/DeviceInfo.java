package com.chunarevsa.Website.Entity.payload;

import com.chunarevsa.Website.Entity.DeviceType;

public class DeviceInfo {

	private String deviceId;

	private DeviceType deviceType;

	private String notificationToken;

	public DeviceInfo() {}

	public DeviceInfo(String deviceId, DeviceType deviceType, String notificationToken) {
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.notificationToken = notificationToken;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public DeviceType getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getNotificationToken() {
		return this.notificationToken;
	}

	public void setNotificationToken(String notificationToken) {
		this.notificationToken = notificationToken;
	}


	@Override
	public String toString() {
		return "{" +
			" deviceId='" + getDeviceId() + "'" +
			", deviceType='" + getDeviceType() + "'" +
			", notificationToken='" + getNotificationToken() + "'" +
			"}";
	}




	
}
