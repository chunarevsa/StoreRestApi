package com.chunarevsa.Website.security.jwt;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// 2
// Из User в UserDetails, По сути User для SpringSecurity
public class JwtUser implements UserDetails{

	private final Long id;
	private final String username;
	private final String password;
	private final String email;
	private final String avatar;  // Переделать в фаил - доделать
	private final boolean enabled;
	private final Date lastPasswordResetDate;

	// Роли 
	private final Collection<? extends GrantedAuthority> authorities;

	public JwtUser(
				Long id, 
				String username, 
				String password, 
				String email, 
				String avatar, 
				Collection<? extends GrantedAuthority> authorities, 
				boolean enabled, 
				Date lastPasswordResetDate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.avatar = avatar;
		this.authorities = authorities;
		this.enabled = enabled;
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities; // Роли
	} 

	@JsonIgnore
	public Long getId() {return id;}

	@Override
	public String getUsername() {return username;}

	@JsonIgnore
	@Override
	public String getPassword() {return password;}

	public String getEmail() {return this.email;}
	public String getAvatar() {return this.avatar;}
	public boolean getEnabled() {return this.enabled;}
	public Date getLastPasswordResetDate() {return this.lastPasswordResetDate;}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {return true;}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {return true;}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {return true;}

	@Override
	public boolean isEnabled() {return enabled;}
	
} 
