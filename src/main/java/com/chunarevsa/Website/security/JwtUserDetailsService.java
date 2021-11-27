package com.chunarevsa.Website.security;

import java.util.Optional;

import com.chunarevsa.Website.Entity.User;
import com.chunarevsa.Website.repo.UserRepository;
import com.chunarevsa.Website.security.jwt.JwtUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	@Autowired
	public JwtUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		Optional<User> user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User with email: " + email + " not found");
		}

		return user.map(JwtUser::new)
					.orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user email in the database for " + email));
	}

	public UserDetails loadUserById(Long id) {
		System.out.println("loadUserById");
		Optional<User> user = userRepository.findById(id);
		
		return user.map(JwtUser::new)
				  .orElseThrow(() -> new UsernameNotFoundException("Couldn't find a matching user id in the database for " + id));
  }
	
}  
