package com.chunarevsa.website.repo;

import com.chunarevsa.website.entity.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<UserAccount, Long> {
	
}
