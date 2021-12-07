package com.chunarevsa.Website.service.inter;

import java.util.Optional;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.payload.RegistrationRequest;
import com.chunarevsa.Website.Entity.token.RefreshToken;
import com.chunarevsa.Website.dto.LoginRequest;

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
