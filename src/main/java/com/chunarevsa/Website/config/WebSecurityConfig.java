package com.chunarevsa.Website.config;

import com.chunarevsa.Website.security.JwtUserDetailsService;
import com.chunarevsa.Website.security.jwt.JwtAuthenricationEntryPoint;
import com.chunarevsa.Website.security.jwt.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 7

@Configuration
@EnableWebSecurity (debug = true)
@EnableJpaRepositories (basePackages = "com.chunarevsa.Website.repo") // - доделать
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtUserDetailsService jwtUserDetailsService;

	private final JwtAuthenricationEntryPoint jwtAuthenricationEntryPoint;

	@Autowired
	public WebSecurityConfig(JwtUserDetailsService jwtUserDetailsService, JwtAuthenricationEntryPoint jwtAuthenricationEntryPoint) {
		this.jwtUserDetailsService = jwtUserDetailsService;
		this.jwtAuthenricationEntryPoint = jwtAuthenricationEntryPoint;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
   	return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter JwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override // ? - доделать
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override // ? - доделать
	public void configure (WebSecurity webSecurity) {
		webSecurity.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
		"/swagger-ui.html", "/webjars/**");
	}

	/* @Override
	protected void configure (HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.cors()
					.and()
						.httpBasic().disable() // ? доделать
						.csrf().disable() // защита от взлома
						.exceptionHandling().authenticationEntryPoint(jwtAuthenricationEntryPoint)
					.and()	
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
									).hasRole("ROLE_ADMIN")
						.anyRequest().authenticated(); // остально только для авторизованых	
		
		httpSecurity.addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	} */

	// Доступ ко всем url
	@Override
	protected void configure (HttpSecurity httpSecurity) throws Exception {
		System.out.println("configure (HttpSecurity) ");
		System.out.println("configure (HttpSecurity) cors - ok");
		httpSecurity.cors()
					.and()
						.httpBasic().disable() // ? доделать
						.csrf().disable() // защита от взлома
						.exceptionHandling().authenticationEntryPoint(jwtAuthenricationEntryPoint)
					.and()	
						// сессии пока отключены - доделать
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
						.authorizeRequests()
						.antMatchers( // Доступны без авторизации
									"/item/**",
									"/auth/**",
									"/registration/**",
									"/activate/*",
									"/auth/login",
									"/auth/**",
									"/currency/**",
									"/admin/**",
									"/user/",
									"/user/**"
									).permitAll()
						.anyRequest().authenticated(); 
		System.out.println("configure (HttpSecurity) cors - ok");
		httpSecurity.addFilterBefore(JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		System.out.println("configure (HttpSecurity) - ok");
	}



} 
