package com.chunarevsa.Website.event;

import java.time.Instant;
import java.util.Date;

import com.chunarevsa.Website.Entity.payload.LogoutRequest;

import org.springframework.context.ApplicationEvent;

public class UserLogoutSuccess extends ApplicationEvent {
	
	private final String userEmail;
	private final String token;
	private final transient LogoutRequest logoutRequest;
	private final Date eventTime;

	public UserLogoutSuccess(
				String userEmail, 
				String token,
				LogoutRequest logoutRequest) {
		super(userEmail);
		this.userEmail = userEmail;
		this.token = token;
		this.logoutRequest = logoutRequest;
		this.eventTime = Date.from(Instant.now());
	}


	public String getUserEmail() {
		return this.userEmail;
	}


	public String getToken() {
		return this.token;
	}

	public LogoutRequest getLogoutRequest() {
		return logoutRequest;
	}

	public Date getEventTime() {
		return this.eventTime;
	}

}
