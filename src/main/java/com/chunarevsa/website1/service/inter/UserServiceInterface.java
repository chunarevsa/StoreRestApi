package com.chunarevsa.website1.service.inter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.chunarevsa.website1.dto.InventoryUnitDto;
import com.chunarevsa.website1.dto.UserDto;
import com.chunarevsa.website1.dto.UserInventoryDto;
import com.chunarevsa.website1.dto.UserProfileDto;
import com.chunarevsa.website1.entity1.Item;
import com.chunarevsa.website1.entity1.User;
import com.chunarevsa.website1.payload.LogOutRequest;
import com.chunarevsa.website1.payload.RegistrationRequest;
import com.chunarevsa.website1.security.jwt.JwtUser;

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

	User findByUsername(String username);

	User findByEmail(String email);

} 
