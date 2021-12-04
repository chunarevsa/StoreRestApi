package com.chunarevsa.Website.Entity;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "USER")
public class User extends Base { 

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 1)
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

	@Column(name = "AVATAR") //TODO:
	private String avatar;

	@Column(name = "IS_EMAIL_VERIFIED", nullable = false)
	private Boolean isEmailVerified;

	@Column(name = "BALANCE") 
	private String balance;

	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USER_ID")
	private Set<Item> items = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER) // Каскады - доделать
	@JoinTable(name = "USER_AUTHORITY", // связь через промежуточную таблицу 
			// колонка 1 называется USER_ID и ссылается на USER_ID из user
			joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") },
			// колонка 2 называется ROLE_ID и ссылается на ROLE_ID
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
	private Set<Role> roles = new HashSet<>();

	@OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USER_ID")
	private Set<Account> accounts = new HashSet<>();
	
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
		this.items = user.items;
		this.roles = user.roles;
		this.accounts = user.accounts;
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


	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
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
			", items='" + getItems() + "'" +
			", roles='" + getRoles() + "'" +
			", accounts='" + getAccounts() + "'" +
			"}";
	}

}
