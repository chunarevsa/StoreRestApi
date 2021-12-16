package com.chunarevsa.website.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "Logout request", description = "Logout request")
public class LogOutRequest {
	
	@Valid
	@NotNull(message = "Device info cannot be null")
	@ApiModelProperty(value = "Информация об устройстве", required = true, dataType = "object", allowableValues = "A valid " +
            "deviceInfo object")
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
