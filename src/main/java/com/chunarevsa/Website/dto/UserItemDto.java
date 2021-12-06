package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.UserItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserItemDto {
	
	private Long id;
	private String name;
	private String type;
	private String description;

	public UserItemDto() {
	}

	public static UserItemDto fromUser (UserItem userItem) {
		UserItemDto userItemDto = new UserItemDto();
		userItemDto.setId(userItem.getId());
		userItemDto.setName(userItem.getName());
		userItemDto.setType(userItem.getType());
		userItemDto.setDescription(userItem.getDescription());
		return userItemDto;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", name='" + getName() + "'" +
			", type='" + getType() + "'" +
			", description='" + getDescription() + "'" +
			"}";
	}

}
