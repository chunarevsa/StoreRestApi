package com.chunarevsa.Website.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.Account;
import com.chunarevsa.Website.Entity.InventoryUnit;
import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.UserDevice;
import com.chunarevsa.Website.Entity.UserInventory;
import com.chunarevsa.Website.Entity.payload.RegistrationRequest;
import com.chunarevsa.Website.Exception.UserLogoutException;
import com.chunarevsa.Website.dto.InventoryUnitDto;
import com.chunarevsa.Website.dto.LogOutRequest;
import com.chunarevsa.Website.dto.UserDto;
import com.chunarevsa.Website.dto.UserInventoryDto;
import com.chunarevsa.Website.dto.UserProfileDto;
import com.chunarevsa.Website.repo.UserRepository;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// TODO: Добавить обработку исключений (доделать)
@Service
public class UserService implements UserServiceInterface{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;
	private final UserDeviceService userDeviceService;
	private final RefreshTokenService refreshTokenService;
	private final UserInventoryService userInventoryService;
	private final AccountService accountService;

	@Autowired
	public UserService(
				UserRepository userRepository,
				RoleService roleService,
				PasswordEncoder passwordEncoder,
				UserDeviceService userDeviceService,
				RefreshTokenService refreshTokenService,
				UserInventoryService userInventoryService,
				AccountService accountService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.userDeviceService = userDeviceService;
		this.refreshTokenService = refreshTokenService;
		this.userInventoryService = userInventoryService;
		this.accountService = accountService;
	}

	/**
	 * Регистрация пользователя
	 */
	@Override 
	public Optional<User> addNewUser (RegistrationRequest registerRequest) {
		User newUser = new User();
		Boolean isAdmin = registerRequest.getRegisterAsAdmin();
		newUser.setEmail(registerRequest.getEmail());		
		newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		newUser.setUsername(registerRequest.getUsername());
		newUser.addRoles(getRoles(isAdmin));
		newUser.setActive(true);
		newUser.setIsEmailVerified(false);
		UserInventory userInventory = new UserInventory();
		newUser.setUserInventory(userInventory);
		return Optional.of(newUser);
	}

	/**
	 * Получение свего профиля
	 */
	@Override
	public Optional<UserProfileDto> getMyUserProfile(JwtUser jwtUser) {
		String username = jwtUser.getUsername().toString();
		User user = findByUsername(username).get();
		return Optional.of(UserProfileDto.fromUser(user));
	}
	
	/**
	 * Получение информации о пользователе
	 */
	@Override
	public Optional<UserDto> getUserProfile (String username) {
		User user = findByUsername(username).get();
		return Optional.of(UserDto.fromUser(user));
	}

	/**
	 * Получение своего инвенторя
	 */
	public Optional<UserInventoryDto> getUserInventory(JwtUser jwtUser) {
		User user = findByUsername(jwtUser.getUsername()).get();
		return userInventoryService.getUserInventory(user);
	}

	/**
	 * Получение списка всех пользователей
	 */
	public List<UserDto> findAllUsersDto() {
		List<User> listUsers = userRepository.findByActive(true);
		return listUsers.stream()
		.map(user -> UserDto.fromUser(user) ).collect(Collectors.toList());
	} 

	/**
	 * Logout
	 * Происходит по конкретному девайсу
	 */
	public void logout(JwtUser jwtUser, @Valid LogOutRequest logOutRequestDto) {

		String deviceId = logOutRequestDto.getDeviceInfo().getDeviceId();
		UserDevice userDevice = userDeviceService.findByUserId(jwtUser.getId())
				.filter(device -> device.getDeviceId().equals(deviceId))
				.orElseThrow(() -> new UserLogoutException(
							logOutRequestDto.getDeviceInfo().getDeviceId(), "Invalid device Id"));
		
		refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
	}

	/**
	 * Получение сохраненного инвенторя
	 */
	public Set<InventoryUnitDto> getSavedInventoryUnit(
							JwtUser jwtUser, String currencyTitle,
							String cost,String amountItems,Item item) {

		String username = jwtUser.getUsername().toString();
		User user = findByUsername(username).get();
		Set<Account> userAccounts = user.getAccounts();

		Set<Account> newUserAccounts = accountService.getNewUserAccounts(userAccounts, currencyTitle, cost, amountItems);
		user.setAccounts(newUserAccounts); // надо ли?

		User savedUser = saveUser(user).get(); // может в самый низ
		UserInventory userInventory = savedUser.getUserInventory();

		// Добавление UserItem в инвентарь
		Set<InventoryUnit> inventoryUnits = userInventoryService.addUserItem(userInventory, item, amountItems);
		saveUser(savedUser);
		
		return inventoryUnits.stream()
				.map(unit -> InventoryUnitDto.fromUser(unit)).collect(Collectors.toSet());
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> saveUser(User user) {
		return Optional.of(userRepository.save(user));
  	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	/**
	 * Проврка может ли быть новый пользователь админом 
	 */
	private Set<Role> getRoles(Boolean isAdmin) {
		Set<Role> roles = new HashSet<>(roleService.findAll());
		if (!isAdmin) {
			roles.removeIf(Role::isAdminRole);
	  }
		return roles;
	}

} 
