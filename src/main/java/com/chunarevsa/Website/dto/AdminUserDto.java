package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
	// Поля доступные админу
	private Long id;
	private String username;
	private String avatar;
	private String email;
	private Boolean active;

	public AdminUserDto() {
	}

	public AdminUserDto(Long id, String username, String avatar, String email, Boolean active) {
		this.id = id;
		this.username = username;
		this.avatar = avatar;
		this.email = email;
		this.active = active;
	}

	public User toUser () {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setAvatar(avatar);
		user.setEmail(email);
		user.setActive(active);
		return user;
	}

	public static AdminUserDto fromUser (User user) {
		AdminUserDto adminUserDto = new AdminUserDto();
		adminUserDto.setId(user.getId());
		adminUserDto.setUsername(user.getUsername());
		adminUserDto.setAvatar(user.getAvatar());
		adminUserDto.setEmail(user.getEmail());
		adminUserDto.setActive(user.isActive());
		return adminUserDto;
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

	public Boolean isActive() {
		return this.active;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}




} 
