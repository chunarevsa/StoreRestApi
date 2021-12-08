package com.chunarevsa.Website.event.listener;

import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.event.UserRegistrationComplete;
import com.chunarevsa.Website.service.EmailVerificationTokenService;
import com.chunarevsa.Website.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationCompleteListener implements ApplicationListener<UserRegistrationComplete> { 
	
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
	@Async // ? - доделать
	public void onApplicationEvent(UserRegistrationComplete userRegistrationComplete) {
		System.out.println("onApplicationEvent");
		sendEmailVerification(userRegistrationComplete);
	}

	private void sendEmailVerification(UserRegistrationComplete userRegistrationComplete) {
		System.out.println("sendEmailVerification");
		
		User user = userRegistrationComplete.getUser();
		String token = emailVerificationTokenService.createNewToken();
		emailVerificationTokenService.createVirficationToken(user, token);

		String userEmail =  user.getEmail();
		String emailConfirmationUrl = 
					userRegistrationComplete.getRedirectUrl().queryParam("token", token).toUriString();

		try {
			mailService.sendMessageVerification(userEmail, emailConfirmationUrl);
		} catch (Exception e) {
			System.err.println("sendEmailVerificatione - ERROR");
			System.err.println(e); // TODO: искл
		}
	
	}

	

}
