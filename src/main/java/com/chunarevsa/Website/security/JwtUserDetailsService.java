package com.chunarevsa.Website.security;


import java.util.Optional;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.UserService;
import com.chunarevsa.Website.service.inter.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 1, 4

@Service
public class JwtUserDetailsService implements UserDetailsService{

	private final UserService userService;

	@Autowired
	public JwtUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	//По username делаем JwtUser
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		Optional<User> user = userService.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User with email: " + email + " not found");
		}

		return user.map(JwtUser::new)
					.orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user email in the database for " + email));
	}

	public UserDetails loadUserById(Long id) {
		System.out.println("loadUserById");
		Optional<User> dbUser = userService.findByid(id);
		
		return dbUser.map(JwtUser::new)
				  .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user id in the database for " + id));
  }
	
}  
