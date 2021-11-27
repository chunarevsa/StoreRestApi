package com.chunarevsa.Website.security.jwt;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// 5
// Генерация токена на основании username, roles и тек. времени
@Component
public class JwtTokenProvider {

	private static final String AUTHORITIES_CLAIM = "authorities";
	private final String secret;
	private final Long jwtExpiration;

	public JwtTokenProvider(
					@Value("${jwt.token.secret}") String secret, 
					@Value("${jwt.token.expired}") Long jwtExpiration, 
					UserDetailsService userDetailsService) {
		this.secret = secret;
		this.jwtExpiration = jwtExpiration;
	}	

	// Создание токена
	public String createToken (JwtUser jwtUser) {
		System.out.println("createToken");

		Instant expiryDate = Instant.now().plusMillis(jwtExpiration);
		String authorities = getUserAuthotities(jwtUser);

		System.out.println("createToken - ok");
		return Jwts.builder()
					.setSubject(Long.toString( jwtUser.getId() ))
					.setIssuedAt(Date.from(Instant.now())) // Время создания токена
					.setExpiration(Date.from(expiryDate)) // До скольки годен
					.signWith(SignatureAlgorithm.HS512, secret) // метод кодирования
					.claim(AUTHORITIES_CLAIM, authorities)
					.compact();
	}

	private String getUserAuthotities(JwtUser jwtUser) {
		System.out.println("getUserAuthotities");
		return jwtUser.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
	}

	public Long getUserIdFromJWT(String token) {
		System.out.println("getUserIdFromJWT");
		Claims claims = Jwts.parser()
								.setSigningKey(secret)
								.parseClaimsJws(token)
								.getBody();

		System.out.println("getUserIdFromJWT - ok");
		return Long.parseLong(claims.getSubject());
	}

	public List<GrantedAuthority> getAuthoritiesFromJWT(String token) {
		
		Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
		System.out.println("secret is " + secret);
		System.out.println("claims is " + claims);
		System.out.println("Claims - ok");
		System.out.println("to String :" + claims.get(AUTHORITIES_CLAIM).toString());

		return Arrays.stream(claims.get(AUTHORITIES_CLAIM).toString().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
	}

	public long getExpiryDuration() {
		return jwtExpiration;
  }
} 
