package com.chunarevsa.Website.repo;

import com.chunarevsa.Website.models.Merchs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MerchsRepository extends CrudRepository<Merchs, Long> {
	Page<Merchs> findAll (Pageable pageable);
	
}
