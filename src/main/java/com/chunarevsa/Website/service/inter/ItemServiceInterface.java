package com.chunarevsa.Website.service.inter;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.ItemRepository;

public interface ItemServiceInterface {
	/* void itemIsPresent(long id, ItemRepository itemRepository) throws NotFound;
	void activeValidate (long id, Item item) throws NotFound;
	void bodyIsNotEmpty (Item bodyItem) throws FormIsEmpty; */
	Item getItem (long id);
	IdByJson getIdByJson (Item bodyItem, ItemRepository itemRepository);
	Item overrideItem (long id, Item bodyItem, ItemRepository itemRepository);
} 
