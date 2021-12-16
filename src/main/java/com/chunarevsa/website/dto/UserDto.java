package com.chunarevsa.website.dto;

import com.chunarevsa.website.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	
	private Long id;
	private String username;
	private String avatar;

	public UserDto() {
	}

	public UserDto(Long id, String username, String avatar) {
		this.id = id;
		this.username = username;
		this.avatar = avatar;
		
	}

	public User toUser () {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setAvatar(avatar);
		return user;
	}

	public static UserDto fromUser (User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setAvatar(user.getAvatar());
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

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", username='" + getUsername() + "'" +
			", avatar='" + getAvatar() + "'" +
			"}";
	}

} 
