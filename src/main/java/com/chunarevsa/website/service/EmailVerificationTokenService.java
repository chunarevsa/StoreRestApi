package com.chunarevsa.website.service;

import java.time.Instant;
import java.util.UUID;

import com.chunarevsa.website.entity.User;
import com.chunarevsa.website.entity.token.EmailVerificationToken;
import com.chunarevsa.website.entity.token.TokenStatus;
import com.chunarevsa.website.exception.InvalidTokenRequestException;
import com.chunarevsa.website.exception.ResourceNotFoundException;
import com.chunarevsa.website.repo.EmailVerificationTokenRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationTokenService {
	
	private static final Logger logger = LogManager.getLogger(EmailVerificationTokenService.class);

	private final EmailVerificationTokenRepository emailVerificationTokenRepository;
	@Value("${jwt.token.expired}")
	private Long tokenExpired;

	@Autowired
	public EmailVerificationTokenService(EmailVerificationTokenRepository emailVerificationTokenRepository) {
		this.emailVerificationTokenRepository = emailVerificationTokenRepository;
	}

	public void createVirficationToken(User user, String token) {
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
		emailVerificationToken.setToken(token);
		emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
		emailVerificationToken.setUser(user);
		emailVerificationToken.setExpiryDate(Instant.now().plusMillis(tokenExpired));
		logger.info("Generated Email verification token :" );
		emailVerificationTokenRepository.save(emailVerificationToken);
	}

	public void verifyExpiration(EmailVerificationToken verificationToken) {
		
		if (verificationToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			logger.info("Токен просрочен");
			throw new InvalidTokenRequestException("Email Verification Token", verificationToken.getToken(),
					"Токен просрочен");
		}
	}

	public String createNewToken() {
		return UUID.randomUUID().toString();
	}

	public EmailVerificationToken findByToken(String token) {
		return emailVerificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("EmailVerificationToken", "token", token));
	}

	public EmailVerificationToken save (EmailVerificationToken verificationToken) {
		return emailVerificationTokenRepository.save(verificationToken);
	}
	
}
