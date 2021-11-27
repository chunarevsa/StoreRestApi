package com.chunarevsa.Website.event;

import java.time.Instant;
import java.util.Date;

import com.chunarevsa.Website.dto.LogOutRequestDto;

import org.springframework.context.ApplicationEvent;

public class UserLogoutSuccess extends ApplicationEvent {
	
	private final String userEmail;
	private final String token;
	private final transient LogOutRequestDto logoutRequestDto;
	private final Date eventTime;

	public UserLogoutSuccess(
				String userEmail, 
				String token,
				LogOutRequestDto logoutRequestDto) {
		super(userEmail);
		this.userEmail = userEmail;
		this.token = token;
		this.logoutRequestDto = logoutRequestDto;
		this.eventTime = Date.from(Instant.now());
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public String getToken() {
		return this.token;
	}

	public Date getEventTime() {
		return this.eventTime;
	}

	public LogOutRequestDto getLogoutRequestDto () {
		return logoutRequestDto;
	}

}
