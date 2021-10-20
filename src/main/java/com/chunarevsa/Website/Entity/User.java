package com.chunarevsa.Website.Entity;

import java.util.List;
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

	private String username;
	private String password;
	private String email;
	private String avatar;
	private Status status;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", // связь через промежуточную таблицу через колонки:
		//колонка 1 называется user_id и ссылается на id из user
		joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, 
		//колонка 2 называется role_id и ссылается на id из role
		inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	private List<Role> roles;
	
	
}
