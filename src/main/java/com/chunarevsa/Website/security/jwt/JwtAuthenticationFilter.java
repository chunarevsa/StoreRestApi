package com.chunarevsa.Website.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${jwt.header}")
	private String tokenRequestHeader;

	@Value("${jwt.header.prefix}")
	private String tokenRequestHeaderPrefix;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
}
