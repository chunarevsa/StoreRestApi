package com.chunarevsa.Website.service.inter;

import java.util.Optional;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.dto.DomesticCurrencyRequest;



public interface DomesticCurrencyServiceInterface {
	 // TODO:
	// Создание
	public Optional<DomesticCurrency> addCurrency (DomesticCurrencyRequest currencyRequest) throws InvalidPriceFormat;

	// Запись параметров
	public Optional<DomesticCurrency> overrideCurrency (String title, DomesticCurrencyRequest currencyRequest);

	// Удаление
	public void deleteCurrency(String title);
	
}	

