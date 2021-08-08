package com.chunarevsa.Website.dto.Item;

import com.chunarevsa.Website.Entity.Items;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.ItemsRepository;

public interface ItemService {
	void itemIsPresent(long id, ItemsRepository itemsRepository) throws NotFound;
	void activeValidate (long id, Items item) throws NotFound;
	void costValidate (Items newItem) throws InvalidPriceFormat;
	void bodyIsNotEmpty (Items newItem) throws FormIsEmpty;
	IdByJson getIdByJson (Items newItem, ItemsRepository itemsRepository);
	Items overrideItem (long id, Items editItem, ItemsRepository itemsRepository);
} 
