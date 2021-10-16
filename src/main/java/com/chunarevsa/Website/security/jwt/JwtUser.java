package com.chunarevsa.Website.security.jwt;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUser implements UserDetails{

	private final Long id;
	private final String username;
	private final String password;
	private final String email;
	private final String avatar;
	private final Collection<? extends GrantedAuthority> authorities;

	private final boolean enabled;
	private final Date lastPasswordResetdate;


	public JwtUser(
				Long id, 
				String username, 
				String password, 
				String email, 
				String avatar, 
				Collection<? extends GrantedAuthority> authorities, 
				boolean enabled, 
				Date lastPasswordResetdate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.avatar = avatar;
		this.authorities = authorities;
		this.enabled = enabled;
		this.lastPasswordResetdate = lastPasswordResetdate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
	public Date getLastPasswordResetdate() {return this.lastPasswordResetdate;}

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