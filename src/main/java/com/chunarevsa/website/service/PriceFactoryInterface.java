package com.chunarevsa.website.service;

import com.chunarevsa.website.entity.Price;
import com.chunarevsa.website.payload.PriceRequest;

public interface PriceFactoryInterface {

	Price createPrice(PriceRequest priceRequest);
}
