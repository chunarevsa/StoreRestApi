
package com.chunarevsa.Website.security;

import java.util.Optional;

import com.chunarevsa.Website.entity.User;
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

		Optional<User> user = userRepository.findByEmail(email);

		return user.map(JwtUser::new) // TODO: искл
					.orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + email));
	}

	public UserDetails loadUserById(Long id) {

		Optional<User> user = userRepository.findById(id);
		return user.map(JwtUser::new)
				  .orElseThrow(() -> new UsernameNotFoundException("Не удалось найти пользователя " + Long.toString(id)));
  }
	
}  
