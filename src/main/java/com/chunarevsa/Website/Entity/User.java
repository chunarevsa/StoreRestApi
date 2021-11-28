package com.chunarevsa.Website.Entity;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "USER")
public class User extends Base { 

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy =  GenerationType.IDENTITY) //mb SEQUENCE
	private Long id;

	@Column(name = "USERNAME", unique = true, nullable = false)
	private String username;

	@Column(name = "EMAIL", unique = true)
	@NotBlank (message = "Еmail cannot be null")
	private String email;

	@Column(name = "PASSWORD")
	@NotNull (message = "Password cannot be null")
	private String password;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	// TODO: 
	private String avatar;

	@ManyToMany(fetch = FetchType.EAGER) // Каскады - доделать
	@JoinTable(name = "USER_AUTHORITY", // связь через промежуточную таблицу 
			// колонка 1 называется USER_ID и ссылается на USER_ID из user
			joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") },
			// колонка 2 называется ROLE_ID и ссылается на ROLE_ID
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Role> roles = new HashSet<>();

	@Column(name = "IS_EMAIL_VERIFIED", nullable = false)
	private Boolean isEmailVerified;
	
	public User() {
		super(); // ? - доделать
	}

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.email = user.email;
		this.password = user.password;
		this.active = user.active;
		this.avatar = user.avatar;
		this.roles = user.roles;
		this.isEmailVerified = user.isEmailVerified;
	}

	public void addRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
   }

	public void addRoles(Set<Role> roles) {
		roles.forEach(this::addRole);
  	}
	
	public void verificationConfirmed() {
		setIsEmailVerified(true);
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Boolean isIsEmailVerified() {
		return this.isEmailVerified;
	}

	public Boolean getIsEmailVerified() {
		return this.isEmailVerified;
	}

	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", username='" + getUsername() + "'" +
			", email='" + getEmail() + "'" +
			", password='" + getPassword() + "'" +
			", active='" + isActive() + "'" +
			", avatar='" + getAvatar() + "'" +
			", roles='" + getRoles() + "'" +
			", isEmailVerified='" + isIsEmailVerified() + "'" +
			"}";
	}


}
