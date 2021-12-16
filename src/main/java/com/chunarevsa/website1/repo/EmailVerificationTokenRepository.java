package com.chunarevsa.website1.repo;

import java.util.Optional;

import com.chunarevsa.website1.entity1.token.EmailVerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
	
	Optional<EmailVerificationToken> findByToken (String token);
	
}
