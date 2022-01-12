package com.chunarevsa.website.service;

import com.chunarevsa.website.entity.Item;
import com.chunarevsa.website.payload.ItemRequest;

import org.springframework.stereotype.Service;

@Service
public class ItemFactory implements ItemFactoryInterface {

	@Override
	public Item createItem(ItemRequest itemRequest) {
		Item newItem = new Item();
		newItem.setName(itemRequest.getName());
		newItem.setType(itemRequest.getType());
		newItem.setActive(itemRequest.getActive());
		Item item = addMoreInfo(newItem, itemRequest);
		return item;
	}

	protected Item addMoreInfo(Item newItem, ItemRequest itemRequest) {
		return newItem;
	}
	
}
