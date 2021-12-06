package com.chunarevsa.Website.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.UserDevice;
import com.chunarevsa.Website.Entity.UserInventory;
import com.chunarevsa.Website.Entity.payload.RegistrationRequest;
import com.chunarevsa.Website.Exception.UserLogoutException;
import com.chunarevsa.Website.dto.ItemDto;
import com.chunarevsa.Website.dto.LogOutRequestDto;
import com.chunarevsa.Website.dto.UserDto;
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

	@Autowired
	public UserService(
				UserRepository userRepository,
				RoleService roleService,
				PasswordEncoder passwordEncoder,
				UserDeviceService userDeviceService,
				RefreshTokenService refreshTokenService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.userDeviceService = userDeviceService;
		this.refreshTokenService = refreshTokenService;
	}

	@Override 
	public User addNewUser (RegistrationRequest registerRequest) {
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
		return newUser;
	}

	public Optional<UserProfileDto> getMyUserProfile(JwtUser jwtUser) {
		String username = jwtUser.getUsername().toString();
		User user = findByUsername(username).get();
		return Optional.of(UserProfileDto.fromUser(user));
	}
	
	public Optional<UserDto> getUserProfile (String username) {
		User user = findByUsername(username).get();
		return Optional.of(UserDto.fromUser(user));
	}

	/* public Set<ItemDto> getMyItems(JwtUser jwtUser) {
		User user = findByUsername(jwtUser.getUsername().toString()).get();
		Set<Item> items = user.getItems();
		Set<ItemDto> itemsDto =	items.stream()
				.map(item -> ItemDto.fromUser(item)).collect(Collectors.toSet());
		return itemsDto;
	} */

	public Optional<User> saveUser(User user) {
		return Optional.of(userRepository.save(user));
  	}
	
	private Set<Role> getRoles(Boolean isAdmin) {
		Set<Role> roles = new HashSet<>(roleService.findAll());
		// Проврка может ли быть новый пользователь админом 
		if (!isAdmin) {
			roles.removeIf(Role::isAdminRole);
	  }
		return roles;
	}

	@Override
	public List<User> getAll() {
		List<User> result = userRepository.findAll();
		return result;
	}

	@Override
	public Optional<User> findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(Long id) {

		User user = userRepository.findById(id).orElse(null);
		if (user == null) {

			return null;
	  }
		return user;
	}

	// TODO: Переделать на выключение (доделать) 
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);

	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findByid(Long id) {
		return userRepository.findById(id);
	}

	public List<UserDto> findAllUsersDto() {
		
		List<User> listUsers = userRepository.findByActive(true);

		return listUsers.stream()
		.map(user -> UserDto.fromUser(user) ).collect(Collectors.toList());

	}

	public void logout(JwtUser jwtUser, 
					@Valid LogOutRequestDto logOutRequestDto) {
		String deviceId = logOutRequestDto.getDeviceInfo().getDeviceId();
		UserDevice userDevice = userDeviceService.findByUserId(jwtUser.getId())
						.filter(device -> device.getDeviceId().equals(deviceId))
						.orElseThrow(() -> new UserLogoutException(
							logOutRequestDto.getDeviceInfo().getDeviceId(), "Invalid device Id"));
		
		refreshTokenService.deleteById(userDevice.getRefreshToken().getId());

	}
	
} 
