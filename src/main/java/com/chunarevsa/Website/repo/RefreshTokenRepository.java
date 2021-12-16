package com.chunarevsa.website.repo;

import java.util.Optional;

import com.chunarevsa.website.entity.token.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	@Override
   Optional<RefreshToken> findById(Long id);

   Optional<RefreshToken> findByToken(String token);

}
