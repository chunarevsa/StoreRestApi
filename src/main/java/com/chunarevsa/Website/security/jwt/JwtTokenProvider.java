package com.chunarevsa.Website.security.jwt;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.Exception.JwtAuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// 5
// Генерация токена на основании username, roles и тек. времени
@Component
public class JwtTokenProvider {

	private static final String AUTHORITIES_CLAIM = "authorities";
	private final String secret;
	private final Long jwtExpiraton;

	public JwtTokenProvider(
					@Value("${jwt.token.secret}") String secret, 
					@Value("${jwt.token.expired}") Long jwtExpiraton, 
					UserDetailsService userDetailsService) {
		this.secret = secret;
		this.jwtExpiraton = jwtExpiraton;
	}	

	// Создание токена
	public String createToken (JwtUser jwtUser) {
		System.out.println("createToken");
		Instant now = Instant.now();
		Instant expiryDate = now.plusMillis(jwtExpiraton);

		String authorities = getUserAuthotities(jwtUser);
		System.out.println("createToken - ok");
		return Jwts.builder()
					.setSubject(Long.toString( jwtUser.getId() ))
					.setIssuedAt(Date.from(now)) // Время создания токена
					.setExpiration(Date.from(expiryDate)) // До скольки годен
					.signWith(SignatureAlgorithm.HS256, secret) // метод кодирования
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
		return Arrays.stream(claims.get(AUTHORITIES_CLAIM).toString().split(","))
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}


	/* @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
   }
	
	@PostConstruct
	public void init () {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	} */

	/* // Получение данных на основе токена
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	} 

	// Возвращение токена по username ??? зачем? - доделать
  	public String getUsername(String token) {
	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	// Анализ токена (Bearer_{token})
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization"); 
		if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
			 return bearerToken.substring(7, bearerToken.length());
			 // 7 - ? (доделать)
		}
		return null;
  	}
	
	// Валидация токена 
	public boolean validateToken(String token) {
		//Используем Claims - проверка когда токен был создан (или когда "испортится")
		//Нужен secret из app.prop
		 try {
			// проверка токена через secret 
			 Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			 // Проверка просроченности токена
			 if (claims.getBody().getExpiration().before(new Date())) {
				  return false;
			 }

			 return true;
		 } catch (JwtException | IllegalArgumentException e) {
			 throw new JwtAuthenticationException("JWT token is expired or invalid");
		} 
  	}

	// Получение списка ролей 
	private List<String> getRoleNames (List<Role> userRoles) {
		List<String> result = new ArrayList<>(); 
		userRoles.forEach(role -> { //вместо stream
			System.out.println("ADD role --- ERORR");
			//result.add(role.getRole());
		});
		return result;

	} */
} 
