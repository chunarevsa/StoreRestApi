package com.chunarevsa.Website.cache;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.chunarevsa.Website.event.UserLogoutSuccess;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.jodah.expiringmap.ExpiringMap;

@Component
public class LoggedOutJwtTokenCache {
	
	private static final Logger logger = LogManager.getLogger(LoggedOutJwtTokenCache.class);

	private final ExpiringMap<String, UserLogoutSuccess> tokenEventMap;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public LoggedOutJwtTokenCache(@Value("${jwt.cache.logoutToken.maxSize}") int maxSize , JwtTokenProvider jwtTokenProvider) {
		this.tokenEventMap = ExpiringMap.builder().variableExpiration()
																.maxSize(maxSize)
																.build();
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public void markLogoutEventForToken(UserLogoutSuccess event) {
		String token = event.getToken();
		if (tokenEventMap.containsKey(token)) {
			logger.info("Токен " + event.getUserEmail() + " уже есть в кэше" );
		} else {
			Date tokenExpiryDate = jwtTokenProvider.getTokenExpiryFromJwt(token);
			long ttlForToken = getTTLForToken(tokenExpiryDate);
			tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
		}
	}

	private long getTTLForToken(Date tokenExpiryDate) {
		long secondAtExpity = tokenExpiryDate.toInstant().getEpochSecond();
		long secondAtLogout = Instant.now().getEpochSecond();
		return Math.max(0, secondAtExpity - secondAtLogout);
	}

	public UserLogoutSuccess getLogoutEventForToken(String token) {
		return tokenEventMap.get(token);
	}

}
