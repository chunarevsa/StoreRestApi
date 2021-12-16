package com.chunarevsa.website1.entity1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.chunarevsa.website1.entity1.token.RefreshToken;

@Entity(name = "USER_DEVICE")
public class UserDevice extends DateAudit {
	
	@Id
	@Column(name = "USER_DEVICE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_device_seq")
	@SequenceGenerator(name = "user_device_seq", allocationSize = 1)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(name = "DEVICE_TYPE")
	@Enumerated(value = EnumType.STRING)
	private DeviceType deviceType;

	@Column(name = "NOTIFICATION_TOKEN")
	private String notificationToken;

	@Column(name = "DEVICE_ID", nullable = false)
	private String deviceId;

	@OneToOne(optional = false, mappedBy = "userDevice")
	private RefreshToken refreshToken;

	@Column(name = "IS_REFRESH_ACTIVE")
	private Boolean isRefreshActive;

	public UserDevice() {
	}

	public UserDevice(Long id, 
							User user, 
							DeviceType deviceType, 
							String notificationToken, 
							String deviceId, 
							RefreshToken refreshToken, 
							Boolean isRefreshActive) {
		this.id = id;
		this.user = user;
		this.deviceType = deviceType;
		this.notificationToken = notificationToken;
		this.deviceId = deviceId;
		this.refreshToken = refreshToken;
		this.isRefreshActive = isRefreshActive;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public RefreshToken getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(RefreshToken refrechToken) {
		this.refreshToken = refrechToken;
	}

	public Boolean isIsRefreshActive() {
		return this.isRefreshActive;
	}

	public Boolean getIsRefreshActive() {
		return this.isRefreshActive;
	}

	public void setIsRefreshActive(Boolean isRefreshActive) {
		this.isRefreshActive = isRefreshActive;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", user='" + getUser() + "'" +
			", deviceType='" + getDeviceType() + "'" +
			", notificationToken='" + getNotificationToken() + "'" +
			", deviceId='" + getDeviceId() + "'" +
			", refreshToken='" + getRefreshToken() + "'" +
			", isRefreshActive='" + isIsRefreshActive() + "'" +
			"}";
	}

}
