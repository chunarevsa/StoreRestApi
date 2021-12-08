package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.entity.payload.JwtAuthenticationResponse;
import com.chunarevsa.Website.entity.payload.RegistrationRequest;
import com.chunarevsa.Website.entity.token.RefreshToken;
import com.chunarevsa.Website.event.UserRegistrationComplete;
import com.chunarevsa.Website.exception.UserLoginException;
import com.chunarevsa.Website.exception.UserRegistrationException;
import com.chunarevsa.Website.payload.LoginRequest;
import com.chunarevsa.Website.payload.ApiResponse;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.AuthService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth/")
@Api(value = "Authorization", description = "Авторизация пользователей")
public class AuthController {

	private static final Logger logger = LogManager.getLogger(AuthController.class);

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
	@ApiOperation(value = "Регистрация пользователя")
	@PostMapping ("/register")
	public ResponseEntity registration (@Valid @RequestBody RegistrationRequest registrationRequest ) {
		
		return authService.registrationUser(registrationRequest)
					.map(user -> {
						UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/registrationConfirmation");
						UserRegistrationComplete userRegistrationComplete = new UserRegistrationComplete(user, urlBuilder);
						applicationEventPublisher.publishEvent(userRegistrationComplete);
						logger.info("Пользователь зарегистрировался: " + user);
						return ResponseEntity.ok(new ApiResponse(true, "Для завершения регистрации перейдите по ссылке в письме"));
					}).orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Пользователь с такой почтой уже существует"));
	}

	/**
	 * Подтверждение учетной записи
	 * @param token
	 */
	@ApiOperation(value = "Подтверждение учетной записи")
	@GetMapping("/registrationConfirmation")
	public ResponseEntity confirmRegistration (@RequestParam("token") String token) {

		return authService.confirmEmailRegistration(token)
					.map(user -> ResponseEntity.ok().body("Учётная запись подтверждена")).orElseThrow();
	}

	/**
	 * Логин по почте, паролю и устройству
	 * @param loginRequestDto
	 */
	@ApiOperation(value = "Логин по почте, паролю и устройству")
	@PostMapping("/login")
	public ResponseEntity login (@Valid @RequestBody LoginRequest loginRequestDto) {

		Authentication authentication = authService.authenticateUser(loginRequestDto)
			.orElseThrow(() -> new UserLoginException("Не удалось войти в систему - " + loginRequestDto));

		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		logger.info("Вход в систему  " + jwtUser.getUsername());
		SecurityContextHolder.getContext().setAuthentication(authentication);  
		
		return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequestDto)
					.map(RefreshToken::getToken)
					.map(refreshToken -> {
						String jwtToken = authService.createToken(jwtUser);
						return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,  jwtTokenProvider.getExpiryDuration()));
					}).orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequestDto + "]"));

	} 


} 
