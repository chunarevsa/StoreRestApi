package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	private Long id;
	private String username;
	private String avatar;
	private String email;

	public UserDto() {
	}

	public UserDto(Long id, String username, String avatar, String email) {
		this.id = id;
		this.username = username;
		this.avatar = avatar;
		this.email = email;
	}

	public User toUser () {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setAvatar(avatar);
		user.setEmail(email);
		return user;
	}

	public static UserDto fromUser (User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setAvatar(user.getAvatar());
		userDto.setEmail(user.getEmail());
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

	



} 
