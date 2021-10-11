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


	// Проверка на формат числа
	public void amountValidate (Price priceBody) throws InvalidPriceFormat {
		int i = Integer.parseInt(priceBody.getAmount());
		if (i < 0) {
			throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
		}
	}

	// Проверка на незаполеннные данные
	
	public void bodyIsNotEmpty (Price priceBody) throws FormIsEmpty {
		if (
		priceBody.getAmount().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
	}

	public void saveAllPrice(Item bodyItem) throws NotFound {
		Set<Price> pricesSet = bodyItem.getPrices();
		for (Price price : pricesSet) {
			try {
				currencyValid.currencyIsPresent(price.getCurrencyCode());
			} catch (Exception e) { // потом сделать NullPoint
				throw new NotFound(HttpStatus.NOT_FOUND);
			}
		}	
		priceRepository.saveAll(bodyItem.getPrices());
	}

	/* @Override
	public Price overrideItem (long id, Price priceBody, PriceRepository priceRepository) {
		Price price = priceRepository.findById(id).orElseThrow();
		price.setAmount(priceBody.getAmount());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		price.setActive(priceBody.getActive());
		return price;
	} */

	
}
