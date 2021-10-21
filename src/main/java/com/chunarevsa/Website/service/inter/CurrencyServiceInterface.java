package com.chunarevsa.Website.service.inter;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.DublicateCurrency;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdDto;


public interface CurrencyServiceInterface {
	// Создание
	public Currency addCurrency(Currency bodyCurrency) throws FormIsEmpty, DublicateCurrency;

	// Получение по id
	public Currency getCurrency (Long id) throws NotFound;

	// Получение по code
	public Currency getCurrencyByCode (String code) throws NotFound;

	// Запись параметров
	public Currency overrideItem (long id, Currency bodyCurrency) throws DublicateCurrency, NotFound, FormIsEmpty;

	public void deleteCurrency(long id) throws NotFound;

	// Id в JSON
	public IdDto getIdByJson (Currency bodyCurrency) throws NotFound;

}