package com.chunarevsa.Website.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.chunarevsa.Website.dto.InventoryUnitDto;
import com.chunarevsa.Website.dto.UserDto;
import com.chunarevsa.Website.dto.UserInventoryDto;
import com.chunarevsa.Website.dto.UserProfileDto;
import com.chunarevsa.Website.entity.Account;
import com.chunarevsa.Website.entity.InventoryUnit;
import com.chunarevsa.Website.entity.Item;
import com.chunarevsa.Website.entity.Role;
import com.chunarevsa.Website.entity.User;
import com.chunarevsa.Website.entity.UserDevice;
import com.chunarevsa.Website.entity.UserInventory;
import com.chunarevsa.Website.exception.ResourceNotFoundException;
import com.chunarevsa.Website.exception.UserLogoutException;
import com.chunarevsa.Website.payload.LogOutRequest;
import com.chunarevsa.Website.payload.RegistrationRequest;
import com.chunarevsa.Website.repo.UserRepository;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserServiceInterface{

	private static final Logger logger = LogManager.getLogger(UserService.class);

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
		newUser.setBalance( Double.toString(0.0) );
		UserInventory userInventory = new UserInventory();
		newUser.setUserInventory(userInventory);
		return Optional.of(newUser);
	}

	/**
	 * Получение свего профиля
	 */
	@Override
	public Optional<UserProfileDto> getMyUserProfile(JwtUser jwtUser) {
		String username = jwtUser.getUsername();
		User user = findByUsername(username);
		return Optional.of(UserProfileDto.fromUser(user));
	}
	
	/**
	 * Получение информации о пользователе
	 */
	@Override
	public Optional<UserDto> getUserProfile (String username) {
		User user = findByUsername(username);
		if (!user.isActive()) {
			logger.error("Пользователь " + user.getUsername() + " Не активен");
			throw new ResourceNotFoundException("Пользоватеоь", "username", username);
		}
		return Optional.of(UserDto.fromUser(user));
	}

	/**
	 * Получение своего инвенторя
	 */
	public Optional<UserInventoryDto> getUserInventory(JwtUser jwtUser) {
		User user = findByUsername(jwtUser.getUsername());
		return Optional.of(userInventoryService.getUserInventory(user));
	}

	/**
	 * Получение списка всех пользователей
	 */
	public List<UserDto> findAllUsersDto() {
		List<User> activeUsers = userRepository.findByActive(true);
		if (activeUsers == null) {
			logger.error("Нет активных пользователей");
			throw new ResourceNotFoundException("User", "active", true);
		}

		return activeUsers.stream() .map(activeUser -> UserDto.fromUser(activeUser))
					.collect(Collectors.toList());
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
	public Set<InventoryUnitDto> getSavedInventoryUnit(JwtUser jwtUser, String currencyTitle,
			String cost, String amountItems, Item item) {

		String username = jwtUser.getUsername().toString();
		User user = findByUsername(username);
		Set<Account> userAccounts = user.getAccounts();

		Set<Account> newUserAccounts = accountService.getNewUserAccounts(userAccounts, currencyTitle, cost, amountItems);
		user.setAccounts(newUserAccounts); 

		User savedUser = saveUser(user); 
		UserInventory userInventory = savedUser.getUserInventory();

		// Добавление Item в инвентарь
		Set<InventoryUnit> inventoryUnits = userInventoryService.addItem(userInventory, item, amountItems);
		saveUser(savedUser);
		logger.info(item.getName() + " добавлен пользователю " + savedUser.getUsername());
		return inventoryUnits.stream()
				.map(unit -> InventoryUnitDto.fromUser(unit)).collect(Collectors.toSet());
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

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
	}

	public User saveUser(User user) {
		return userRepository.save(user);
  	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

} 
