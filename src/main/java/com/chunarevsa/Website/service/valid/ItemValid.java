package com.chunarevsa.Website.service.valid;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.repo.ItemRepository;

import org.springframework.stereotype.Service;

@Service
public class ItemValid {
	
	private final ItemRepository itemRepository;

	public ItemValid(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}


	// Проверка на наличие 
	public boolean itemIsPresent (long id) {
		return itemRepository.findById(id).isPresent(); 
	}

	// Проверка не выключен ли active = true
	public boolean itemIsActive (Long id) {
		return itemRepository.findById(id).orElseThrow().getActive();
	}

	// Проверка на незаполеннные данные
	public boolean bodyIsEmpty (Item bodyItem) throws FormIsEmpty {
		if (
			bodyItem.getSku().isEmpty()  || 
			bodyItem.getName().isEmpty() || 
			bodyItem.getType().isEmpty() || 
			bodyItem.getDescription().isEmpty()) {
				return true;
		}
		return false;
	}
}
