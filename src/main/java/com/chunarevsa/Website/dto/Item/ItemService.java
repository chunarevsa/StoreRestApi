package com.chunarevsa.Website.dto.Item;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.ItemRepository;

public interface ItemService {
	void itemIsPresent(long id, ItemRepository itemRepository) throws NotFound;
	void activeValidate (long id, Item item) throws NotFound;
	void bodyIsNotEmpty (Item bodyItem) throws FormIsEmpty;
	IdByJson getIdByJson (Item bodyItem, ItemRepository itemRepository);
	Item overrideItem (long id, Item bodyItem, ItemRepository itemRepository);
} 
