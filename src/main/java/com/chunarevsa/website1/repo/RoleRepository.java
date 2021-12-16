package com.chunarevsa.website1.repo;

import com.chunarevsa.website1.entity1.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole (String role); 
} 
