package com.chunarevsa.Website.Entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
	USER, ADMIN;

	public String getAuthority () {
		return name();
	}
}
/* import java.util.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role extends Base {

	private String role;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<User> users;

	
} */
