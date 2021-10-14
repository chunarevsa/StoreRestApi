package com.chunarevsa.Website.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class UserRole extends Base {

	private String role;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<User> users;

}
