package com.chunarevsa.Website.event.listener;

import com.chunarevsa.Website.Entity.payload.DeviceInfo;
import com.chunarevsa.Website.cache.LoggedOutJwtTokenCache;
import com.chunarevsa.Website.event.UserLogoutSuccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserLogoutSuccessListener implements ApplicationListener<UserLogoutSuccess> {
	
	private final LoggedOutJwtTokenCache tokenCache;

	@Autowired
	public UserLogoutSuccessListener(LoggedOutJwtTokenCache tokenCache) {
		this.tokenCache = tokenCache;
	}

	public void onApplicationEvent(UserLogoutSuccess event) {
		System.out.println("onApplicationEvent");
		if (event != null) {
			DeviceInfo deviceInfo = event.getLogoutRequestDto().getDeviceInfo();
			System.out.println(deviceInfo.toString());
			tokenCache.markLogoutEventForToken(event);
		}
		
	}

}
