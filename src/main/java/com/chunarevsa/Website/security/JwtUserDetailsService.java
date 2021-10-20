package com.chunarevsa.Website.security;


import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.security.jwt.jwtUserFactory;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService{

	private final UserServiceInterface userService;

	@Autowired
	public JwtUserDetailsService(UserServiceInterface userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}

		JwtUser jwtUser = jwtUserFactory.create(user);
		log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
		return jwtUser;
	}
	
}
