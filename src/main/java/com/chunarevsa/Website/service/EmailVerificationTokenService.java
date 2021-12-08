package com.chunarevsa.Website.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.entity.token.EmailVerificationToken;
import com.chunarevsa.Website.entity.token.TokenStatus;
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

		if (verificationToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			/* throw new InvalidTokenRequestException("Email Verification Token", token.getToken(),
					"Expired token. Please issue a new request"); */
			System.out.println("Expired token. Please issue a new request"); // TODO:
		}
	}

	public void createVirficationToken(User user, String token) {
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
		emailVerificationToken.setToken(token);
		emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
		emailVerificationToken.setUser(user);
		emailVerificationToken.setExpiryDate(Instant.now().plusMillis(tokenExpired));

		emailVerificationTokenRepository.save(emailVerificationToken);
	}

	public EmailVerificationToken save (EmailVerificationToken verificationToken) {
		return emailVerificationTokenRepository.save(verificationToken);
	}

	public String createNewToken() {
		return UUID.randomUUID().toString();
	}

}
