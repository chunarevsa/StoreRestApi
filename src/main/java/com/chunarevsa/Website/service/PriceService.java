package com.chunarevsa.Website.service;

import java.util.*;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.repo.PriceRepository;
import com.chunarevsa.Website.service.valid.CurrencyValid;
import com.chunarevsa.Website.service.valid.PriceValid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class PriceService  {

	private final PriceRepository priceRepository;
	private final PriceValid priceValid;
	private final CurrencyValid currencyValid;

	public PriceService(
				PriceRepository priceRepository,
				PriceValid priceValid, 
				CurrencyValid currencyValid) {
		this.priceRepository = priceRepository;
		this.priceValid = priceValid;
		this.currencyValid = currencyValid;
	}

	public void saveAllPrice(Item bodyItem) throws NotFound, InvalidPriceFormat, FormIsEmpty {
		Set<Price> pricesSet = bodyItem.getPrices();
		for (Price price : pricesSet) {
			if (!priceValid.amountIsCorrect(price)) {
				throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
			}
			if (priceValid.bodyIsEmpty(price)) {
				throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
			}
			try {
				currencyValid.currencyIsPresent(price.getCurrencyCode());
			} catch (Exception e) { // потом сделать NullPoint
				throw new NotFound(HttpStatus.NOT_FOUND);
			}
		}	
		priceRepository.saveAll(bodyItem.getPrices());
	}
	
}
