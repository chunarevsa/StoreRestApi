package com.chunarevsa.Website.service.inter;

import java.util.Set;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.PriceRequest;

public interface PriceServiceInterface {

	public Set<Price> saveAllPrice(Set<PriceRequest> setPriceRequest, Item item);

	public void deletePrice (Long id) throws NotFound;
	
}
