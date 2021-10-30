package com.chunarevsa.Website.config;

import com.chunarevsa.Website.security.jwt.JwtConfigurer;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// 7

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtTokenProvider jwtTokenProvider;

	private static final String LOGIN_ENDPOINT = "/**"; // /auth
	
	//private static final String ADMIN_ENDPOINT = "/**";
   


	@Autowired
	public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure (HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.
						httpBasic().disable() // ? доделать
						.csrf().disable() // защита от взлома
						// сессии пока отключены - доделать
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
						.authorizeRequests() // все запросы должны быть авторизованы
						//Url доступные для залогиненых ()
						.antMatchers(LOGIN_ENDPOINT).permitAll()
						.antMatchers("/**").permitAll()
						//Url доступные для ADMIN
						//.antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
						//остальные запросы должны быть authenticated
						.anyRequest().authenticated()
					.and()
						.apply(new JwtConfigurer(jwtTokenProvider));

	}
} 
