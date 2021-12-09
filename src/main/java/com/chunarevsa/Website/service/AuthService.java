package com.chunarevsa.Website.service;

import java.util.Optional;

import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.entity.UserDevice;
import com.chunarevsa.Website.entity.payload.RegistrationRequest;
import com.chunarevsa.Website.entity.token.EmailVerificationToken;
import com.chunarevsa.Website.entity.token.RefreshToken;
import com.chunarevsa.Website.exception.AlredyUseException;
import com.chunarevsa.Website.payload.LoginRequest;
import com.chunarevsa.Website.security.jwt.JwtTokenProvider;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.inter.AuthServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

// TODO: log
@Service
public class AuthService implements AuthServiceInterface {

	private static final Logger logger = LogManager.getLogger(AuthService.class);

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	private final EmailVerificationTokenService emailVerificationTokenService;
	private final AuthenticationManager authenticationManager;
	private final UserDeviceService userDeviceService;
	private final RefreshTokenService refreshTokenService;

	@Autowired
	public AuthService(
					UserService userService, 
					JwtTokenProvider jwtTokenProvider,
					EmailVerificationTokenService emailVerificationTokenService,
					AuthenticationManager authenticationManager,
					UserDeviceService userDeviceService,
					RefreshTokenService refreshTokenService) {
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.emailVerificationTokenService = emailVerificationTokenService;
		this.authenticationManager = authenticationManager;
		this.userDeviceService = userDeviceService;
		this.refreshTokenService = refreshTokenService;
	}

	/**
	 * Регистрация новго пользователя
	 * @param registrationRequest
	 * @return
	 */
	@Override
	public Optional<User> registrationUser (RegistrationRequest registrationRequest) {

		String email = registrationRequest.getEmail();
		if (emailAlreadyExists(email)) {
			logger.error("Email " + email + " уже занят");
			throw new AlredyUseException("Email"); // TODO: искл
	  }  
	  User newUser = userService.addNewUser(registrationRequest).get();
	  User savedNewUser = userService.saveUser(newUser).get();
	  logger.info("Зарегистрирован новый пользователь " + email);
	  return Optional.ofNullable(savedNewUser);
	}

	/**
	 * Подтверждение учетной записи
	 */
	@Override
	public Optional<User> confirmEmailRegistration(String token) { 

		EmailVerificationToken verificationToken = emailVerificationTokenService.findByToken(token)
				.orElseThrow(); // TODO: обработка исключений

		User registeredUser = verificationToken.getUser();
		if  (registeredUser.getIsEmailVerified()) {
			logger.error("Пользователь " + registeredUser.getEmail() + " уже потверждён");
			return Optional.of(registeredUser);
		}

		emailVerificationTokenService.verifyExpiration(verificationToken);
		verificationToken.setConfirmedStatus();

		emailVerificationTokenService.save(verificationToken);
		registeredUser.verificationConfirmed();

		userService.saveUser(registeredUser);
		return Optional.of(registeredUser);
	}

	/**
	 * Логин по почте, паролю и устройству
	 */
	@Override
	public Optional<Authentication> authenticateUser(LoginRequest loginRequestDto) {
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
		return Optional.ofNullable(authenticationManager.authenticate(user));
	}

	/**
	 * Обновление токена для устройства
	 */
	@Override
	public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication,
			LoginRequest loginRequestDto) {
		User jwtUser = (User) authentication.getPrincipal();

		userDeviceService.findByUserId(
			jwtUser.getId()).map(UserDevice::getRefreshToken)
								 .map(RefreshToken::getId)
								 .ifPresent(refreshTokenService::deleteById);
		
		UserDevice userDevice = userDeviceService.createUserDevice(loginRequestDto.getDeviceInfo());
		RefreshToken refreshToken = refreshTokenService.createRefrechToken();

		userDevice.setUser(jwtUser);
		userDevice.setRefreshToken(refreshToken);

		refreshToken.setUserDevice(userDevice);
		refreshToken = refreshTokenService.save(refreshToken);
		logger.info("Добавлено нове устройство " + userDevice.getDeviceId() 
				+ " для пользователя " + jwtUser.getUsername());
		return Optional.ofNullable(refreshToken);
	}

	/**
	 * Создание нового токена
	 */
	public String createToken (JwtUser jwtUser) {
		return jwtTokenProvider.createToken(jwtUser);
	}	

	/**
	 * Проверка на наличие пользователя с такой почтой
	 */
	private boolean emailAlreadyExists(String userEmail) {
		return userService.existsByEmail(userEmail);
	}

}
