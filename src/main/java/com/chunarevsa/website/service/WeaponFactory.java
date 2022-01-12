package com.chunarevsa.website.service;

import com.chunarevsa.website.entity.Item;
import com.chunarevsa.website.entity.Weapon;
import com.chunarevsa.website.payload.ItemRequest;

public class WeaponFactory extends ItemFactory {
	
	@Override
	protected Item addMoreInfo(Item newItem, ItemRequest itemRequest) {
		Weapon weapon = (Weapon) newItem;
		itemRequest.getDescription();
		weapon.setDescription(itemRequest.getDescription());
		return newItem;
	}

}
