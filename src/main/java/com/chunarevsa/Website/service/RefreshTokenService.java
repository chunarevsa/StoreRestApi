package com.chunarevsa.Website.service;

import java.time.Instant;

import com.chunarevsa.Website.Entity.token.RefreshToken;
import com.chunarevsa.Website.repo.RefreshTokenRepository;
import com.chunarevsa.Website.util.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${jwt.refresh.duration}")
	private Long refreshTokenDurationMs;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public void deleteById(Long id) {
		refreshTokenRepository.deleteById(id);
	}
	
	public RefreshToken createRefrechToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setExpityDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(Util.generateRandomUuid());
		refreshToken.setRefreshCount(0L); // TODO: ?

		return refreshToken;
	}

	public RefreshToken save(RefreshToken refreshToken) {
		return refreshTokenRepository.save(refreshToken);
	}

	
}
