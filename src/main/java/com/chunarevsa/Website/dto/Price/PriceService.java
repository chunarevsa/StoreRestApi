package com.chunarevsa.Website.dto.Price;

import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
// import com.chunarevsa.Website.repo.PriceRepository;

public interface PriceService {
	void amountValidate (Price price) throws InvalidPriceFormat;
	void bodyIsNotEmpty (Price price) throws FormIsEmpty;
	//Price overrideItem (long id, Price priceBody, PriceRepository priceRepository);
}
