package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.User;

public class UserModel {
	
	private Long id;

	private String username;
	private String avatar;
	private String created;

	public UserModel() {}

	public static UserModel toModel (User user) {
		UserModel userModel = new UserModel();
		userModel.setId(user.getId());
		userModel.setUsername(user.getUsername());
		userModel.setAvatar(user.getAvatar());
		userModel.setCreated(user.getCreated());

		return userModel;
	}

	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public String getUsername() {return this.username;}
	public void setUsername(String username) {this.username = username;}

	public String getAvatar() {return this.avatar;}
	public void setAvatar(String avatar) {this.avatar = avatar;}

	public String getCreated() {return this.created;}
	public void setCreated(String created) {this.created = created;}

}
