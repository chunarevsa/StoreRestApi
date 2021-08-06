package com.chunarevsa.Website.dto.Item;

import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.repo.ItemsRepository;

import org.springframework.http.HttpStatus;

public class ItemValidator {

	private ItemValidator itemValidator;
	private long id;
	private ItemsRepository itemsRepository;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ItemsRepository getItemsRepository() {
		return this.itemsRepository;
	}

	public void setItemsRepository(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	public ItemValidator getItemValidator() {
		return this.itemValidator;
	}

	public void setItemValidator(ItemValidator itemValidator) {
		this.itemValidator = itemValidator;
	}

	// Проверка на наличие 
	public void itemIsPresent (long id, ItemsRepository itemsRepository) throws NotFound {
		Boolean item1 = itemsRepository.findById(id).isPresent();
		if (item1 == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}	 
	}

	public ItemValidator(long id, ItemsRepository itemsRepository) {
		this.id = id;
		this.itemsRepository = itemsRepository;
	}


	public ItemValidator() {
	}

}
