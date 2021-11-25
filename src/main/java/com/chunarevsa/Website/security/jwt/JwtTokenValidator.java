package com.chunarevsa.Website.security.jwt;

import com.chunarevsa.Website.Exception.InvalidTokenRequestException;
import com.chunarevsa.Website.cache.LoggedOutJwtTokenCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenValidator {
	
	private final String secret;
	private final LoggedOutJwtTokenCache loggedOutJwtTokenCache;

	@Autowired
	public JwtTokenValidator(
			@Value("${jwt.token.secret}") String secret,
			LoggedOutJwtTokenCache loggedOutJwtTokenCache) {
		this.secret = secret;
		this.loggedOutJwtTokenCache = loggedOutJwtTokenCache;
	}

	public boolean validateToken(String token) throws InvalidTokenRequestException {
		System.out.println("validateToken");
		try {
			System.out.println("secret :" + secret);
			System.out.println(token);
			
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

			System.out.println("jwts");

		} catch (SignatureException ex) {
			// logger.error("Invalid JWT signature");
			 throw new InvalidTokenRequestException("JWT", token, "Incorrect signature");

		} catch (MalformedJwtException ex) {
			 //logger.error("Invalid JWT token");
			 throw new InvalidTokenRequestException("JWT", token, "Malformed jwt token");

		} catch (ExpiredJwtException ex) {
			 //logger.error("Expired JWT token");
			 throw new InvalidTokenRequestException("JWT", token, "Token expired. Refresh required");

		} catch (UnsupportedJwtException ex) {
			 //logger.error("Unsupported JWT token");
			 throw new InvalidTokenRequestException("JWT", token, "Unsupported JWT token");

		} catch (IllegalArgumentException ex) {
			 //logger.error("JWT claims string is empty.");
			 throw new InvalidTokenRequestException("JWT", token, "Illegal argument token");
		}

		System.out.println("validateToken - ok");

		return true;
	}


}
