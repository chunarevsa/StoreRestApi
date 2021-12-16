package com.chunarevsa.website1.repo;

import java.util.Optional;

import com.chunarevsa.website1.entity1.token.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	@Override
   Optional<RefreshToken> findById(Long id);

   Optional<RefreshToken> findByToken(String token);

}
