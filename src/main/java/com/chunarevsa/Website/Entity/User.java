package com.chunarevsa.Website.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
@Data 
public class User extends Base {

	@Column (nullable = false)
	private String username;
	@Column (nullable = false)
	private String password;
	@Column (nullable = false)
	private String email;

	// Переделать в фаил - доделать
	private String avatar;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", // связь через промежуточную таблицу через колонки:
		//колонка 1 называется user_id и ссылается на id из user
		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, 
		//колонка 2 называется role_id и ссылается на id из role
		inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	private List<Role> roles;
	
} 

/* import java.util.*; - доделать (убрать)

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table (name = "users")
public class User extends Base implements UserDetails {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (nullable = false)
	private String username;

	@Column (nullable = false)
	private String password;

	@Transient
	private String password2;

	private Status status;

	@Column (nullable = false)
	private String email;

	private String activationCode;

	// Создание доп. таблицы для хранение для списка роллей, которая соединяется с User через "user_id"
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable (name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING) // Enam храним в ввиде строки 
	private Set<Role> roles;

	public boolean isAdmin () {
		return roles.contains(Role.ADMIN);
	}

	public User() {}

	public User(User user) {
		this.username = user.username;
		this.password = user.password;
		this.password2 = user.password2;
		this.status = user.status;
		this.email = user.email;
		this.activationCode = user.activationCode;
		this.roles = user.roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {return true;}

	@Override
	public boolean isAccountNonLocked() {return true;}

	@Override
	public boolean isCredentialsNonExpired() {return true;}

	@Override
	public boolean isEnabled() {
		System.out.println("ПРОВЕРКА на getStatus в User");
		if (getStatus() == Status.ACTIVE) {
			return true;
		}
		return false;
	}

	public String getUsername() {return this.username;}
	public void setUsername(String username) {this.username = username;}

	public String getPassword() {return this.password;}
	public void setPassword(String password) {this.password = password;}

	public String getPassword2() {return this.password2;}
	public void setPassword2(String password2) {this.password2 = password2;}

	public Status getStatus() {return this.status;}
	public void setStatus(Status status) {this.status = status;}

	public String getEmail() {return this.email;}
	public void setEmail(String email) {this.email = email;}

	public String getActivationCode() {return this.activationCode;}
	public void setActivationCode(String activationCode) {this.activationCode = activationCode;}

	public Set<Role> getRoles() {return this.roles;}
	public void setRoles(Set<Role> roles) {this.roles = roles;}
	
} */


