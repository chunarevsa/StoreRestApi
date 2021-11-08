package com.chunarevsa.Website.repo;

import java.util.Optional;

import com.chunarevsa.Website.Entity.token.EmailVerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
	Optional<EmailVerificationToken> findByToken (String token);
}
