package com.chunarevsa.Website.service.valid;

import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;

import org.springframework.stereotype.Service;

@Service
public class PriceValid {
	// Проверка на формат числа
	
	public boolean amountIsCorrect (Price priceBody) throws InvalidPriceFormat {
		
		int i = Integer.parseInt(priceBody.getAmount());
		if (i < 0) {
			return false;
		}
		return true;
	}

	// Проверка на незаполеннные данные
	public boolean bodyIsEmpty (Price priceBody) throws FormIsEmpty {
		if (priceBody.getAmount().isEmpty()) {
			return true;
		}
		return false;
	}
}
