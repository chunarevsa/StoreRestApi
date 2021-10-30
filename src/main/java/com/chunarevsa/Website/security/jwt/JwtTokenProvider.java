package com.chunarevsa.Website.security.jwt;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.Exception.JwtAuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
// Генерация токена
@Component
public class JwtTokenProvider {

	@Value("${jwt.token.secret}")
	private String secret;

	@Value("${jwt.token.expired}")
	private long validityInMillisec; // можно взять int

	@Autowired
	private UserDetailsService userDetailsService;

	// Нужен будет в UserService (кодировка пароля пользователя)
	@Bean
   public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
   }

	@PostConstruct
	public void init () {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	// Создание токена
	public String createToken (String username, List<Role> roles) {

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", getRoleNames(roles));
		// Время создания токена
		Date now = new Date();
		// До скольки годен
		Date validity = new Date(now.getTime() + validityInMillisec);

		return Jwts.builder()
					.setClaims(claims) // ?
					.setIssuedAt(now) // Время создания токена
					.setExpiration(validity) // До скольки годен
					.signWith(SignatureAlgorithm.HS256, secret) // метод кодирования
					.compact();
	}

	// Получение данных на основе токена
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
			result.add(role.getRole());
		});
		return result;

	}
} 
