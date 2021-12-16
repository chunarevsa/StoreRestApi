package com.chunarevsa.website.repo;

import com.chunarevsa.website.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole (String role); 
} 
