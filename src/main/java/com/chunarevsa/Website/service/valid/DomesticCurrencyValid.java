package com.chunarevsa.Website.service.valid;
/* 
import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.repo.DomesticCurrencyRepository;

import org.springframework.stereotype.Service;

@Service
public class DomesticCurrencyValid {

	private final DomesticCurrencyRepository currencyRepository;

	public DomesticCurrencyValid(DomesticCurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	// Проверка на наличие по ид
	public boolean currencyIsPresent(long id) {

		DomesticCurrency currency = currencyRepository.findById(id).orElse(null);
		if (currency == null) {
			return false;
		}
		return true;  
	}

	 // Проверка на наличие валюты с таким code
	public boolean codeIsPresent (String code) {
		DomesticCurrency currencyByCode = currencyRepository.findByCode(code);
		if (currencyByCode == null) {
			return false;
		}	 
		System.out.println(currencyByCode.getCode());
		return true;
	} 

	// Проверка status
	public boolean currencyIsActive (long id) {
		Boolean isActive = currencyRepository.findById(id).orElseThrow().getActive();
		if (isActive) {
			return true;
		}
		return false;
	}

	// Проверка на незаполеннные данные
	 public boolean bodyIsEmpty (DomesticCurrency bodyCurrency) {
		if (bodyCurrency.getCode().isEmpty()) {
			return true;
		}
		return false;
	} 
	
} */
