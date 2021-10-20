package com.chunarevsa.Website.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.Entity.Status;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class jwtUserFactory {

	public jwtUserFactory() {
	}
	
	public static JwtUser create (User user) {
		return new JwtUser(
				user.getId(), 
				user.getUsername(), 
				user.getPassword(), 
				user.getEmail(), 
				user.getAvatar(), 
				mapToGrantedAuth(new ArrayList<>( user.getRoles() )), // authorities
				user.getStatus().equals(Status.ACTIVE), 
				user.getUpdated());
	}

	private static List<GrantedAuthority> mapToGrantedAuth (List<Role> userRoles) {
		return userRoles.stream()
			.map(role -> new SimpleGrantedAuthority( role.getRole()))
			.collect(Collectors.toList());
	}
}
