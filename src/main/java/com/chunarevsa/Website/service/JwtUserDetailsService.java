
package com.chunarevsa.website.service;

import java.util.Optional;

import com.chunarevsa.website.entity.User;
import com.chunarevsa.website.repo.UserRepository;
import com.chunarevsa.website.security.jwt.JwtUser;

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

		Optional<User> user = userRepository.findByEmail(email);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + email));
	}

	public UserDetails loadUserById(Long id) {

		Optional<User> user = userRepository.findById(id);
		return user.map(JwtUser::new)
			.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + id));
  }
	
}  
