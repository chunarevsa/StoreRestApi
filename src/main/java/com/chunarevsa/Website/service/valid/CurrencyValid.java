package com.chunarevsa.Website.service.valid;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.repo.CurrencyRepository;

import org.springframework.stereotype.Service;

@Service
public class CurrencyValid {

	private final CurrencyRepository currencyRepository;

	public CurrencyValid(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	public boolean currencyIsPresent(String currencyCode) throws NullPointerException {
		Currency currency = currencyRepository.findByCode(currencyCode);
		currency.getCode();
		return false;
	}
	
}
