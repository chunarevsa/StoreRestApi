package com.chunarevsa.Website.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.token.EmailVerificationToken;
import com.chunarevsa.Website.Entity.token.TokenStatus;
import com.chunarevsa.Website.repo.EmailVerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationTokenService {
	
	private final EmailVerificationTokenRepository emailVerificationTokenRepository;
	@Value("${jwt.token.expired}")
	private Long tokenExpired;

	@Autowired
	public EmailVerificationTokenService(EmailVerificationTokenRepository emailVerificationTokenRepository) {
		this.emailVerificationTokenRepository = emailVerificationTokenRepository;
	}

	public Optional<EmailVerificationToken> findByToken(String token) {
		return emailVerificationTokenRepository.findByToken(token);
	}

	public void verifyExpiration(EmailVerificationToken verificationToken) {
		System.out.println("verifyExpiration");
		System.out.println("verificationToken is :" + verificationToken);
		if (verificationToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			/* throw new InvalidTokenRequestException("Email Verification Token", token.getToken(),
					"Expired token. Please issue a new request"); */
			System.out.println("Expired token. Please issue a new request");
		}
		System.out.println("verifyExpiration - ok");
	}

	public EmailVerificationToken save (EmailVerificationToken verificationToken) {
		System.out.println("save");
		return emailVerificationTokenRepository.save(verificationToken);
	}

	public String createNewToken() {
		System.out.println("createNewToken");
		return UUID.randomUUID().toString();
	}

	public void createVirficationToken(User user, String token) {
		
		System.out.println("createVirficationToken");
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
		emailVerificationToken.setToken(token);
		emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
		emailVerificationToken.setUser(user);
		emailVerificationToken.setExpiryDate(Instant.now().plusMillis(tokenExpired));

		emailVerificationTokenRepository.save(emailVerificationToken);

		System.out.println("createVirficationToken - ok");

	}





}
