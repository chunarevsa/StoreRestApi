package com.chunarevsa.Website.service.valid;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Entity.Status;
import com.chunarevsa.Website.repo.CurrencyRepository;

import org.springframework.stereotype.Service;

@Service
public class CurrencyValid {

	private final CurrencyRepository currencyRepository;

	public CurrencyValid(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	// Проверка на наличие по ид
	public boolean currencyIsPresent(long id) {

		Currency currency = currencyRepository.findById(id).orElse(null);
		if (currency == null) {
			return false;
		}
		return true;  
	}

	// Проверка на наличие валюты с таким code
	public boolean codeIsPresent (String code) {
		Currency currencyByCode = currencyRepository.findByCode(code);
		if (currencyByCode == null) {
			return false;
		}	 
		return true;
	}

	// Проверка status
	public boolean currencyIsActive (long id) {
		Status currencyStatus = currencyRepository.findById(id).orElseThrow().getStatus();
		if (currencyStatus == Status.ACTIVE) {
			return true;
		}
		return false;
	}

	// Проверка на незаполеннные данные
	public boolean bodyIsEmpty (Currency bodyCurrency) {
		if (bodyCurrency.getCode().isEmpty()) {
			return true;
		}
		return false;
	}
	
}
