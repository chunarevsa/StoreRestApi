package com.chunarevsa.website.service;

import java.util.Optional;

import com.chunarevsa.website.entity.UserDevice;
import com.chunarevsa.website.payload.DeviceInfo;
import com.chunarevsa.website.repo.UserDeviceRepository;

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
