package com.chunarevsa.website1.repo;

import java.util.Set;

import com.chunarevsa.website1.entity1.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

	Set<Item> findAllByActive(boolean active);

	Page<Item> findAll (Pageable pageable);

}
