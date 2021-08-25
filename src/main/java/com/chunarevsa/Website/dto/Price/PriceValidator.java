package com.chunarevsa.Website.dto.Price;

import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
// import com.chunarevsa.Website.repo.PriceRepository;

import org.springframework.http.HttpStatus;

public class PriceValidator implements PriceService {

	// Проверка на формат числа
	@Override
	public void amountValidate (Price priceBody) throws InvalidPriceFormat {
		int i = Integer.parseInt(priceBody.getAmount());
		if (i < 0) {
			throw new InvalidPriceFormat(HttpStatus.BAD_REQUEST);
		}
	}

	// Проверка на незаполеннные данные
	@Override
	public void bodyIsNotEmpty (Price priceBody) throws FormIsEmpty {
		if (
		priceBody.getAmount().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
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
