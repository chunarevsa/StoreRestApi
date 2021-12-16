package com.chunarevsa.website.entity;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "ROLE")
public class Role  {

	@Id
	@Column (name = "ROLE_ID")
	@GeneratedValue (strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ROLE_NAME")
	@Enumerated (EnumType.STRING)
	@NaturalId // ? - доделать
	private RoleName role;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<User> users = new HashSet<>();

	public Role() {}
	
	public Role(RoleName role) {
		this.role = role;
	}

	public boolean isAdminRole() {
		return null != this && this.role.equals(RoleName.ROLE_ADMIN);
  }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleName getRole() {
		return this.role;
	}

	public void setRole(RoleName role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", role='" + getRole() + "'" +
			", users='" + getUsers() + "'" +
			"}";
	}

} 

