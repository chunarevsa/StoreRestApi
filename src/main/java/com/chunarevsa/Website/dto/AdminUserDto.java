package com.chunarevsa.Website.dto;

/* import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
	private Long id;
	private String username;
	private String avatar;
	private String email;
	private String status;

	public User toUser () {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setAvatar(avatar);
		user.setEmail(email);
		user.setStatus(Status.valueOf(status));
		return user;
	}

	public static AdminUserDto fromUser (User user) {
		AdminUserDto adminUserDto = new AdminUserDto();
		adminUserDto.setId(user.getId());
		adminUserDto.setUsername(user.getUsername());
		adminUserDto.setAvatar(user.getAvatar());
		adminUserDto.setEmail(user.getEmail());
		adminUserDto.setStatus(user.getStatus().name());
		return adminUserDto;
	}
} */
