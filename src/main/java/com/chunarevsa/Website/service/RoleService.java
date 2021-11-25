package com.chunarevsa.Website.service;

import java.util.Collection;

import com.chunarevsa.Website.Entity.Role;
import com.chunarevsa.Website.repo.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public Collection<Role> findAll() {
		return roleRepository.findAll();
	}
}
