package com.chunarevsa.website.service;

import com.chunarevsa.website.entity.Coin;
import com.chunarevsa.website.entity.Item;
import com.chunarevsa.website.payload.ItemRequest;

public class CoinFactory extends ItemFactory {

	@Override
	protected Item addMoreInfo(Item newItem, ItemRequest itemRequest) {
		Coin weapon = (Coin) newItem;
		itemRequest.getDescription();
		weapon.setDescription(itemRequest.getDescription());
		return newItem;
	}
	
}
