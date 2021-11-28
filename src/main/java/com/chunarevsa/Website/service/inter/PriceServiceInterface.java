package com.chunarevsa.Website.service.inter;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.Exception.NotFoundDomesticCurrency;

public interface PriceServiceInterface {

	public void saveAllPrice(Item bodyItem) throws InvalidPriceFormat, FormIsEmpty, NotFoundDomesticCurrency;

	public void deletePrice (Long id) throws NotFound;
	
}
