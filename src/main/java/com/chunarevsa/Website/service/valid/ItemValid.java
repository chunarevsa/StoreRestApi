package com.chunarevsa.Website.service.valid;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Status;
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
		Item item = itemRepository.findById(id).orElse(null);
		if (item == null) {
			return false;
		}
		return true; 
	}

	// Проверка не выключен ли active = true
	public boolean itemIsActive (Long id) {
		
		Status status = itemRepository.findById(id).orElse(null).getStatus();
		System.out.println(status);
		if (status == Status.ACTIVE) {
			return true;
	}
		return false;
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
