package com.chunarevsa.Website.Entity;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "USER")
public class User extends Base { // переделать Base доделать 

	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String email;

	// Переделать в фаил - доделать
	private String avatar;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", // связь через промежуточную таблицу через колонки:
			// колонка 1 называется user_id и ссылается на id из user
			joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
			// колонка 2 называется role_id и ссылается на id из role
			inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<Role> roles = new HashSet<>();

	private Boolean isEmailVerified;
	
	public User() {
		super();
	}

	public void addRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
   }

	public void addRoles(Set<Role> roles) {
		roles.forEach(this::addRole);
  	 }

}
