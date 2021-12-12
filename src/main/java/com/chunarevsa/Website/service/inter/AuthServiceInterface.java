package com.chunarevsa.Website.service.inter;

import java.util.Optional;

import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.entity.token.RefreshToken;
import com.chunarevsa.Website.payload.LoginRequest;
import com.chunarevsa.Website.payload.RegistrationRequest;

import org.springframework.security.core.Authentication;

public interface AuthServiceInterface {
	/**
	 * Регистрация новго пользователя
	 */
	public Optional<User> registrationUser (RegistrationRequest registrationRequest);

	/**
	 * Подтверждение учетной записи
	 */
	public Optional<User> confirmEmailRegistration(String token);

	/**
	 * Логин по почте, паролю и устройству
	 */
	public Optional<Authentication> authenticateUser(LoginRequest loginRequestDto);

	/**
	 * Обновление токена для устройства
	 */
	public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication,
			LoginRequest loginRequestDto);
			
}
