package com.chunarevsa.Website.service.inter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.dto.InventoryUnitDto;
import com.chunarevsa.Website.dto.UserDto;
import com.chunarevsa.Website.dto.UserInventoryDto;
import com.chunarevsa.Website.dto.UserProfileDto;
import com.chunarevsa.Website.entity.Item;
import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.entity.payload.RegistrationRequest;
import com.chunarevsa.Website.payload.LogOutRequest;
import com.chunarevsa.Website.security.jwt.JwtUser;

public interface UserServiceInterface {
	
	/**
	 * Регистрация пользователя
	 */
	Optional<User> addNewUser (RegistrationRequest registerRequest);

	/**
	 * Получение свего профиля
	 */
	Optional<UserProfileDto> getMyUserProfile(JwtUser jwtUser);

	/**
	 * Получение информации о пользователе
	 */
	Optional<UserDto> getUserProfile (String username);

	/**
	 * Получение своего инвенторя
	 */
	Optional<UserInventoryDto> getUserInventory(JwtUser jwtUser);

	/**
	 * Получение списка всех пользователей
	 */
	List<UserDto> findAllUsersDto();

	/**
	 * Logout
	 * Происходит по конкретному девайсу
	 */
	void logout(JwtUser jwtUser, LogOutRequest logOutRequestDto);

	/**
	 * Получение сохраненного инвенторя
	 */
	Set<InventoryUnitDto> getSavedInventoryUnit(JwtUser jwtUser, String currencyTitle,
							String cost,String amountItems,Item item);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

} 
