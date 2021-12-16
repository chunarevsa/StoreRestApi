package com.chunarevsa.website.security.jwt;

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

/**
 * Генерация токена на основании username, roles и тек. времени
 */
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

	/**
	 * Создание токена
	 * @param jwtUser
	 */
	public String createToken (JwtUser jwtUser) {

		Instant expiryDate = Instant.now().plusMillis(jwtExpiration);
		String authorities = getUserAuthotities(jwtUser);
		return Jwts.builder()
					.setSubject(Long.toString( jwtUser.getId() ))
					.setIssuedAt(Date.from(Instant.now())) 
					.setExpiration(Date.from(expiryDate)) 
					.signWith(SignatureAlgorithm.HS512, secret) 
					.claim(AUTHORITIES_CLAIM, authorities)
					.compact();
	}

	/**
	 * Получение авторизации
	 * @param jwtUser
	 */
	private String getUserAuthotities(JwtUser jwtUser) {
		return jwtUser.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
								.setSigningKey(secret)
								.parseClaimsJws(token)
								.getBody();
		return Long.parseLong(claims.getSubject());
	}

	/**
	 * Получения списка ролей у пользоавтеля
	 * @param token
	 */
	public List<GrantedAuthority> getAuthoritiesFromJWT(String token) {
		Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
		return Arrays.stream(claims.get(AUTHORITIES_CLAIM).toString().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
	}

	public long getExpiryDuration() {
		return jwtExpiration;
  }

	public Date getTokenExpiryFromJwt(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
		return claims.getExpiration();
	}
} 
