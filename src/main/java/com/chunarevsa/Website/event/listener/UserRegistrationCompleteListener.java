package com.chunarevsa.Website.event.listener;

import com.chunarevsa.Website.event.UserRegistrationComplete;
import com.chunarevsa.Website.service.EmailVerificationTokenService;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationCompleteListener implements ApplicationListener<UserRegistrationComplete> { 
	
	private final EmailVerificationTokenService emailVerificationTokenService;

	private final MailServ
}
