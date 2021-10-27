package com.chunarevsa.Website.service.inter;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.NotFoundCurrency;

public interface PriceServiceInterface {

	public void saveAllPrice(Item bodyItem) throws InvalidPriceFormat, FormIsEmpty, NotFoundCurrency;

	public void deletePrice (Long id) throws NotFound;
	
}
