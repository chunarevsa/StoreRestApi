package com.chunarevsa.Website.dto.Item;

import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.repo.ItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ItemValidator {
	
	@Autowired
	private ItemsRepository itemsRepository;

	public ItemValidator (ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	public ItemValidator() {
	}

}
