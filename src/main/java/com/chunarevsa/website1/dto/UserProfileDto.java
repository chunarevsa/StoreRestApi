package com.chunarevsa.website1.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.website1.entity1.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDto {

	private Long id;
	private String username;
	private String avatar;
	private String email;
	private String balance;
	private Set<AccountDto> accounts;

	public UserProfileDto() {
	}

	public UserProfileDto(Long id, 
								String username, 
								String avatar, 
								String email, 
								String balance,
								Set<AccountDto> accountsDto) {
		this.id = id;
		this.username = username;
		this.avatar = avatar;
		this.email = email;
		this.balance = balance;
		this.accounts = accountsDto;
	}
	
	public User toUser () {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setAvatar(avatar);
		user.setEmail(email);
		user.setBalance(balance);
		return user;
	}

	public static UserProfileDto fromUser (User user) {
		UserProfileDto userDto = new UserProfileDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setAvatar(user.getAvatar());
		userDto.setEmail(user.getEmail());
		userDto.setBalance(user.getBalance());
		userDto.setAccounts(user.getAccounts().stream()
			.map(AccountDto::fromUser).collect(Collectors.toSet()));
		return userDto;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBalance() {
		return this.balance + " $";
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public Set<AccountDto> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<AccountDto> accountsDto) {
		this.accounts = accountsDto;
	}
	
	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", username='" + getUsername() + "'" +
			", avatar='" + getAvatar() + "'" +
			", email='" + getEmail() + "'" +
			", balance='" + getBalance() + "'" +
			", accountsDto='" + getAccounts() + "'" +
			"}";
	}
	
}
