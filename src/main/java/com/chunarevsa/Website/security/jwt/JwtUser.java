package com.chunarevsa.website.security.jwt;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import com.chunarevsa.website.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class JwtUser extends User implements UserDetails{

	public JwtUser (final User user) {
		super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole().name()))
					.collect(Collectors.toList());
	} 

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return super.getActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.getIsEmailVerified();
	}

	@Override
	 public boolean equals(Object obj) {
		  if (obj == this)
				return true;
		  if (!(obj instanceof JwtUser)) {
				return false;
		  }
		  JwtUser jwtUser = (JwtUser) obj;
		  return Objects.equals(getId(), jwtUser.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
} 
