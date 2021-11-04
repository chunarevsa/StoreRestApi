package com.chunarevsa.Website.config;

import com.chunarevsa.Website.security.jwt.JwtConfigurer;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// 7

@Configuration
@EnableWebSecurity (debug = true)
@EnableGlobalMethodSecurity (prePostEnabled = true)
// @EnableJpaRepositories (basePackages = "com.chunarevsa.Website.repo") - доделать
// @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtTokenProvider jwtTokenProvider;

	//private static final String LOGIN_ENDPOINT = "/auth/login"; 
	//private static final String ADMIN_ENDPOINT = "/admin/**";
   
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
						.authorizeRequests()
							.antMatchers( // Доступны без авторизации
										"/item/**",
										"/registration/**",
										"/activate/*",
										"/auth/login"
										).permitAll()
							.antMatchers( // Доступны для админов
										"/currency/**",
										"/admin/**"
										).hasRole("ADMIN")
							.anyRequest().authenticated() // остально только для авторизованых	
					.and()
						.apply(new JwtConfigurer(jwtTokenProvider)); 
		
		// Вкл общий доступ
		/*  httpSecurity.
						httpBasic().disable() // ? доделать
						.csrf().disable() // защита от взлома
						// сессии пока отключены - доделать
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
						.authorizeRequests() // Доступны без авторизации
							.antMatchers( "/**").permitAll()
							.anyRequest().authenticated() // остально только для авторизованых	
					.and()
						.apply(new JwtConfigurer(jwtTokenProvider)); */

	}
} 
