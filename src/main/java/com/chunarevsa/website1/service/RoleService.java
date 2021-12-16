package com.chunarevsa.website1.service;

import java.util.Collection;

import com.chunarevsa.website1.entity1.Role;
import com.chunarevsa.website1.repo.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	/**
	 * Получение списка всех ролей
	 * @return
	 */
	public Collection<Role> findAll() {
		return roleRepository.findAll();
	}
}
