package com.chunarevsa.website.service;

import com.chunarevsa.website.entity.Item;
import com.chunarevsa.website.payload.ItemRequest;

public interface ItemFactoryInterface {

	Item createItem(ItemRequest itemRequest);
	
}
