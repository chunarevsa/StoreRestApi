package com.chunarevsa.website1.repo;

import java.util.Optional;

import com.chunarevsa.website1.entity1.UserDevice;
import com.chunarevsa.website1.entity1.token.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
	
	@Override
   Optional<UserDevice> findById(Long id);

   Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken);

   Optional<UserDevice> findByUserId(Long userId);
	
}
