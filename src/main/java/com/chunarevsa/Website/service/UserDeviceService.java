package com.chunarevsa.Website.service;

import java.util.Optional;

import com.chunarevsa.Website.entity.UserDevice;
import com.chunarevsa.Website.payload.DeviceInfo;
import com.chunarevsa.Website.repo.UserDeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDeviceService {
	
	private final UserDeviceRepository userDeviceRepository;

	@Autowired
	public UserDeviceService(UserDeviceRepository userDeviceRepository) {
		this.userDeviceRepository = userDeviceRepository;
	}

	/**
	 * Создание нового устройства для пользователя
	 */
	public UserDevice createUserDevice(DeviceInfo deviceInfo) {
		UserDevice userDevice = new UserDevice();
		userDevice.setDeviceId(deviceInfo.getDeviceId());
		userDevice.setDeviceType(deviceInfo.getDeviceType());
		userDevice.setNotificationToken(deviceInfo.getNotificationToken());
		userDevice.setIsRefreshActive(true);
		return userDevice;
	}

	public Optional<UserDevice> findByUserId(Long userId) {
		return userDeviceRepository.findByUserId(userId);
	}

}
