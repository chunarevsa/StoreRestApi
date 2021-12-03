package com.chunarevsa.Website.repo;

import java.util.Optional;

import com.chunarevsa.Website.Entity.UserDevice;
import com.chunarevsa.Website.Entity.token.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
	
	@Override
   Optional<UserDevice> findById(Long id);

   Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken);

   Optional<UserDevice> findByUserId(Long userId);
	
}
