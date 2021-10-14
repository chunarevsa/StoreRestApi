package com.chunarevsa.Website.Entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;


@Entity
@Data 
public class User extends Base {

	private String username;
	private String password;
	private String avatar;
	private boolean active;
	private UserStatus status;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", // связь через промежуточную таблицу через колонки:
		//колонка 1 называется user_id и ссылается на id из user
		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, 
		//колонка 2 называется role_id и ссылается на id из role
		inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	private List<UserRole> roles;
	

	// Конструкторы
	/* public User(User bodyUser) {
		this.username  = bodyUser.username;
		this.password = bodyUser.password;
		this.avatar = bodyUser.avatar;
		this.active = bodyUser.active;
		
		super.this.created = bodyUser.created;
		this.updated = bodyUser.updated;
		this.status =  bodyUser.status;
	}

	public User() {}

	// Getter and Setter

	public String getUsername() {return this.username;}
	public void setUsername(String username) {this.username = username;}

	public String getPassword() {return this.password;}
	public void setPassword(String password) {this.password = password;}

	public String getAvatar() {return this.avatar;}
	public void setAvatar(String avatar) {this.avatar = avatar;}

	public boolean isActive() {return this.active;}
	public boolean getActive() {return this.active;}
	public void setActive(boolean active) {this.active = active;}

	public Date getCreated() {return this.created;}
	public void setCreated(Date created) {this.created = created;}

	public Date getUpdated() {return this.updated;}
	public void setUpdated(Date updated) {this.updated = updated;}

	public UserStatus getActiveRole() {return this.status;}
	public void setActiveRole(UserStatus activeRole) {this.status = activeRole;}
	 */
}
