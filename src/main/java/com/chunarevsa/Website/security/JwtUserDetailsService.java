package com.chunarevsa.Website.security;


import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.security.jwt.JwtUserFactory;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

// 1, 4

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService{

	private final UserServiceInterface userService;

	@Autowired
	public JwtUserDetailsService(UserServiceInterface userService) {
		this.userService = userService;
	}

	//По username делаем JwtUser
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}

		// из User делаем UserDetails (4)
		JwtUser jwtUser = JwtUserFactory.create(user);
		log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
		return jwtUser;
	}
	
} 
