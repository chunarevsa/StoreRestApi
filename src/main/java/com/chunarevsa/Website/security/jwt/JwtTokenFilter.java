package com.chunarevsa.Website.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

// 6
// Фильтрует запросы на наличие валидного токена
public class JwtTokenFilter extends GenericFilterBean{

	private final JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	// Валидация каждого запроса на наличие аутентификации
	@Override
	public void doFilter(
					ServletRequest req, 
					ServletResponse res, 
					FilterChain filterChain) throws IOException, ServletException {
		// Получение токена из запроса (req)
		String token = jwtTokenProvider.resolveToken( (HttpServletRequest) req);
		
		// Если не null и валидный
		if (token != null && jwtTokenProvider.validateToken(token)) {
				// Передача аутентификация
				Authentication auth = jwtTokenProvider.getAuthentication(token);

				if (auth != null) {
						// если не null то аутентифицируем запрос
						SecurityContextHolder.getContext().setAuthentication(auth);
				}
		}

		filterChain.doFilter(req, res);
	}
	
} 
