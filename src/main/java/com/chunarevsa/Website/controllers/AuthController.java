package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.entity.payload.JwtAuthenticationResponse;
import com.chunarevsa.Website.entity.payload.RegistrationRequest;
import com.chunarevsa.Website.entity.token.RefreshToken;
import com.chunarevsa.Website.event.UserRegistrationComplete;
import com.chunarevsa.Website.exception.UserLoginException;
import com.chunarevsa.Website.exception.UserRegistrationException;
import com.chunarevsa.Website.payload.LoginRequest;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth/")
public class AuthController {

	private final AuthService authService;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public AuthController(
					AuthService authService,
					ApplicationEventPublisher applicationEventPublisher,
					JwtTokenProvider jwtTokenProvider) {
		this.authService = authService;
		this.applicationEventPublisher = applicationEventPublisher;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * Регистрация пользователя
	 * "registerAsAdmin": "true" - зарегистрировать администратором
	 * @param registrationRequest
	 */
	@PostMapping ("/register")
	public ResponseEntity registration (@Valid @RequestBody RegistrationRequest registrationRequest ) {
		
		return authService.registrationUser(registrationRequest)
					.map(user -> {
						UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/registrationConfirmation");
						UserRegistrationComplete userRegistrationComplete = new UserRegistrationComplete(user, urlBuilder);
						applicationEventPublisher.publishEvent(userRegistrationComplete);
						return ResponseEntity.ok("Для завершения регистрации перейдите по ссылке в письме");
					}).orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Пользователь с такой почтой уже существует"));
	}

	/**
	 * Подтверждение учетной записи
	 * @param token
	 */
	@GetMapping("/registrationConfirmation")
	public ResponseEntity confirmRegistration (@RequestParam("token") String token) {

		return authService.confirmEmailRegistration(token)
					.map(user -> ResponseEntity.ok().body("Учётная запись подтверждена")).orElseThrow();
	}

	/**
	 * Логин по почте, паролю и устройству
	 * @param loginRequestDto
	 */
	@PostMapping("/login")
	public ResponseEntity login (@Valid @RequestBody LoginRequest loginRequestDto) {

		Authentication authentication = authService.authenticateUser(loginRequestDto)
			.orElseThrow(() -> new UserLoginException("Не удалось войти в систему - " + loginRequestDto));

		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(authentication);  
		
		return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequestDto)
					.map(RefreshToken::getToken)
					.map(refreshToken -> {
						String jwtToken = authService.createToken(jwtUser);
						return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,  jwtTokenProvider.getExpiryDuration()));
					}).orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequestDto + "]"));

	} 


} 
