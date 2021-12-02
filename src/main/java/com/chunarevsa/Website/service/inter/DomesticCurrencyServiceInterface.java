package com.chunarevsa.Website.service.inter;

import java.util.Optional;
import java.util.Set;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.dto.DomesticCurrencyDto;
import com.chunarevsa.Website.dto.DomesticCurrencyRequest;

public interface DomesticCurrencyServiceInterface {

	// Получение страницы всех Currency
	public Set<DomesticCurrencyDto> getCurrenciesDtoFromUser();
	
	// Получить CurrencyDto по title
	public DomesticCurrencyDto getCurrencyDtoByTitle(String title);

	// Добавление Currency
	public Optional<DomesticCurrency> addCurrency (DomesticCurrencyRequest currencyRequest) throws InvalidPriceFormat;

	// Запись параметров
	public Optional<DomesticCurrency> editCurrency (String title, DomesticCurrencyRequest currencyRequest);

	// Удаление (Выключение) Currency
	public Optional<DomesticCurrency> deleteCurrency(String title);
	
}	

