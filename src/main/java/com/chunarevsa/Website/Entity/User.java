package com.chunarevsa.Website.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	private String avatar;
	private boolean active;
	private String created_at;

	// Конструкторы
	public User(User bodyUser) {
		this.username  = bodyUser.username;
		this.password = bodyUser.password;
		this.avatar = bodyUser.avatar;
		this.active = bodyUser.active;
		this.created_at = bodyUser.created_at;
	}

	public User() {}

	// Getter and Setter
	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public String getUsername() {return this.username;}
	public void setUsername(String username) {this.username = username;}

	public String getPassword() {return this.password;}
	public void setPassword(String password) {this.password = password;}

	public String getAvatar() {return this.avatar;}
	public void setAvatar(String avatar) {this.avatar = avatar;}

	public boolean isActive() {return this.active;}
	public boolean getActive() {return this.active;}
	public void setActive(boolean active) {this.active = active;}

	public String getCreated_at() {return this.created_at;}
	public void setCreated_at(String created_at) {this.created_at = created_at;}

}
