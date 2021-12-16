package com.chunarevsa.website.service.inter;

import java.util.Optional;

import com.chunarevsa.website.entity.User;
import com.chunarevsa.website.entity.token.RefreshToken;
import com.chunarevsa.website.payload.LoginRequest;
import com.chunarevsa.website.payload.RegistrationRequest;

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
