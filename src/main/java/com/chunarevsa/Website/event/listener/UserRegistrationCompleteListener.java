package com.chunarevsa.Website.event.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.event.UserRegistrationComplete;
import com.chunarevsa.Website.service.EmailVerificationTokenService;
import com.chunarevsa.Website.service.MailService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationCompleteListener implements ApplicationListener<UserRegistrationComplete> { 
	
	private static final Logger logger = LogManager.getLogger(UserRegistrationCompleteListener.class);

	private final EmailVerificationTokenService emailVerificationTokenService;
	private final MailService mailService;

	@Autowired
	public UserRegistrationCompleteListener(
					EmailVerificationTokenService emailVerificationTokenService, 
					MailService mailService) {
		this.emailVerificationTokenService = emailVerificationTokenService;
		this.mailService = mailService;
	}

	@Override
	@Async // TODO: ? 
	public void onApplicationEvent(UserRegistrationComplete userRegistrationComplete) {
		sendEmailVerification(userRegistrationComplete);
	}

	private void sendEmailVerification(UserRegistrationComplete userRegistrationComplete) {
		
		
		User user = userRegistrationComplete.getUser();
		String token = emailVerificationTokenService.createNewToken();
		emailVerificationTokenService.createVirficationToken(user, token);

		String userEmail =  user.getEmail();
		String emailConfirmationUrl = 
					userRegistrationComplete.getRedirectUrl().queryParam("token", token).toUriString();

		try {
			mailService.sendMessageVerification(userEmail, emailConfirmationUrl);
			logger.info("Cообщение о потверждении отправленно на почту " + userEmail);
		} catch (Exception e) {
			logger.error(e);
			System.err.println(e); // TODO: искл
		}
	
	}

	

}
