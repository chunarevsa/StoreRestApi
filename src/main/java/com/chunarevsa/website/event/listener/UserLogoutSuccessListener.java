package com.chunarevsa.website.event.listener;

import com.chunarevsa.website.cache.LoggedOutJwtTokenCache;
import com.chunarevsa.website.event.UserLogoutSuccess;
import com.chunarevsa.website.payload.DeviceInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserLogoutSuccessListener implements ApplicationListener<UserLogoutSuccess> {
	
	private static final Logger logger = LogManager.getLogger(UserLogoutSuccessListener.class);

	private final LoggedOutJwtTokenCache tokenCache;

	@Autowired
	public UserLogoutSuccessListener(LoggedOutJwtTokenCache tokenCache) {
		this.tokenCache = tokenCache;
	}

	public void onApplicationEvent(UserLogoutSuccess event) {
		if (event != null) {
			DeviceInfo deviceInfo = event.getLogoutRequestDto().getDeviceInfo();
			logger.info("Пользователь " + event.getUserEmail() + " " + deviceInfo.getDeviceId() + "покинул систему");
			tokenCache.markLogoutEventForToken(event);
		}
		
	}

}
