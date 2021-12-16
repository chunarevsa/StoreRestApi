package com.chunarevsa.website.repo;

import java.util.Optional;

import com.chunarevsa.website.entity.token.EmailVerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
	
	Optional<EmailVerificationToken> findByToken (String token);
	
}
