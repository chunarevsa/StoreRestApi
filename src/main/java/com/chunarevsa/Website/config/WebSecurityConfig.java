package com.chunarevsa.Website.config;

import com.chunarevsa.Website.security.JwtUserDetailsService;
import com.chunarevsa.Website.security.jwt.JwtAuthenricationEntryPoint;
import com.chunarevsa.Website.security.jwt.JwtAuthenticationFilter;
import com.chunarevsa.Website.security.jwt.JwtConfigurer;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;
import com.chunarevsa.Website.security.jwt.JwtUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// 7

@Configuration
@EnableWebSecurity (debug = true)
@EnableJpaRepositories (basePackages = "com.chunarevsa.Website.repo") - доделать
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtUserDetailsService jwtUserDetailsService;

	private final JwtAuthenricationEntryPoint jwtAuthenricationEntryPoint;

	@Autowired
	public WebSecurityConfig(JwtUser jwtUser, JwtAuthenricationEntryPoint jwtAuthenricationEntryPoint) {
		this.jwtUser = jwtUser;
		this.jwtAuthenricationEntryPoint = jwtAuthenricationEntryPoint;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	public JwtAuthenticationFilter JwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure (AuthenticationManagerBuilder auth) {
		auth.userDetailsService(jwtUserDetailsService).
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
