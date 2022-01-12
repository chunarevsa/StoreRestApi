package com.chunarevsa.website.service;

import com.chunarevsa.website.entity.Item;
import com.chunarevsa.website.entity.Skin;
import com.chunarevsa.website.payload.ItemRequest;

public class SkinFactory extends ItemFactory {
	
	@Override
	protected Item addMoreInfo(Item newItem, ItemRequest itemRequest) {
		Skin weapon = (Skin) newItem;
		itemRequest.getDescription();
		weapon.setDescription(itemRequest.getDescription());
		return newItem;
	}
}
