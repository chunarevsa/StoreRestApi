package com.chunarevsa.Website.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.Status;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

// 3
// Класс генерации JwtUser (шаблон)
public final class JwtUserFactory {

	public JwtUserFactory() {}
	
	// из User в JwtUser
	public static JwtUser create (User user) {
		return new JwtUser(
				user.getId(), 
				user.getUsername(), 
				user.getPassword(), 
				user.getEmail(), 
				user.getAvatar(), 
				mapToGrantedAuth(new ArrayList<>( user.getRoles() )), // authorities (роли)
				user.getStatus().equals(Status.ACTIVE), 
				user.getUpdated());
	}

	// конвертация Ролей в GrantedAuthority (для SpringSecurity)
	private static List<GrantedAuthority> mapToGrantedAuth (List<Role> userRoles) {
		// проходим по списку UserRole и преобразуем в список GrantedAuthority
		return userRoles.stream()
			.map(role -> new SimpleGrantedAuthority( role.getRole()))
			.collect(Collectors.toList());
	}
} 
