package com.chunarevsa.website.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "USER")
public class User extends DateAudit { 

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 1)
	private Long id;

	@Column(name = "USERNAME", unique = true, nullable = false)
	private String username;

	@Column(name = "EMAIL", unique = true, nullable = false)
	@NotBlank (message = "Еmail cannot be null")
	private String email;

	@Column(name = "PASSWORD")
	@NotNull (message = "Password cannot be null")
	private String password;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	@Column(name = "AVATAR") 
	private String avatar;

	@Column(name = "IS_EMAIL_VERIFIED", nullable = false)
	private Boolean isEmailVerified;

	@Column(name = "BALANCE") 
	private String balance;

	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(name = "USER_AUTHORITY",
			joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") },
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Role> roles = new HashSet<>();

	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USER_ID")
	private Set<Account> accounts = new HashSet<>();

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name ="INVENTORY_ID", unique = true)
	private UserInventory userInventory;
	
	public User() {
		super(); 
	}

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.email = user.email;
		this.password = user.password;
		this.active = user.active;
		this.avatar = user.avatar;
		this.isEmailVerified = user.isEmailVerified;
		this.balance = user.balance;
		this.roles = user.roles;
		this.accounts = user.accounts;
		this.userInventory = user.userInventory;
	}

	public User(Long id, 
					String username, 
					String email, 
					String password, 
					Boolean active, 
					String avatar, 
					Boolean isEmailVerified, 
					String balance, 
					Set<Role> roles, 
					Set<Account> accounts,
					UserInventory userInventory) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
		this.avatar = avatar;
		this.isEmailVerified = isEmailVerified;
		this.balance = balance;
		this.roles = roles;
		this.accounts = accounts;
		this.userInventory = userInventory;
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

	public String getBalance() {
		return this.balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public UserInventory getUserInventory() {
		return this.userInventory;
	}

	public void setUserInventory(UserInventory userInventory) {
		this.userInventory = userInventory;
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
			", isEmailVerified='" + isIsEmailVerified() + "'" +
			", balance='" + getBalance() + "'" +
			", roles='" + getRoles() + "'" +
			", accounts='" + getAccounts() + "'" +
			", userInventory='" + getUserInventory() + "'" +
			"}";
	}
	
}
