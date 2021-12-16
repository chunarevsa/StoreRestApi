package com.chunarevsa.website1.event;

import com.chunarevsa.website1.entity1.User;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class UserRegistrationComplete extends ApplicationEvent {
	
	private transient UriComponentsBuilder redirectUrl;
	private User user;

	public UserRegistrationComplete(User user, UriComponentsBuilder redirectUrl) {
		super(user);
		this.user = user;
		this.redirectUrl = redirectUrl;
	}

	public UriComponentsBuilder getRedirectUrl () {
		return this.redirectUrl;
	}

	public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
