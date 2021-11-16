package com.chunarevsa.Website.cache;

import com.chunarevsa.Website.event.UserLogoutSuccess;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.jodah.expiringmap.ExpiringMap;

@Component
public class LoggedOutJwtTokenCache {
	
	private final ExpiringMap<String, UserLogoutSuccess> tokenEventMap;

	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public LoggedOutJwtTokenCache( @Value("${jwt.cache.logoutToken.maxSize}") int maxSize , JwtTokenProvider jwtTokenProvider) {
		this.tokenEventMap = ExpiringMap.builder().variableExpiration()
																.maxSize(maxSize)
																.build();
		this.jwtTokenProvider = jwtTokenProvider;
	}

	

	
}
