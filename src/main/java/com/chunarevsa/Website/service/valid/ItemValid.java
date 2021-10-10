package com.chunarevsa.Website.service.valid;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.repo.ItemRepository;

import org.springframework.http.HttpStatus;

public class ItemValid {
	
	private final ItemRepository itemRepository;

	public ItemValid(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	// Проверка на наличие 
	public void itemIsPresent (long id) throws NotFound{
		Boolean item = itemRepository.findById(id).isPresent();
		if (item == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}	 
	}

	// Проверка не выключен ли active = true
	public void itemIsActive (Long id) throws NotFound {
		Item item = itemRepository.findById(id).orElseThrow();
		if (item.getActive() == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
	}

	// Проверка на незаполеннные данные
	public void bodyIsNotEmpty (Item bodyItem) throws FormIsEmpty {
		if (
		bodyItem.getName().isEmpty() == true || 
		bodyItem.getSku().isEmpty() == true || 
		bodyItem.getType().isEmpty() == true || 
		bodyItem.getDescription().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
	}
}
