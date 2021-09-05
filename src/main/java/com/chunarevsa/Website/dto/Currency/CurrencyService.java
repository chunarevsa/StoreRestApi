package com.chunarevsa.Website.dto.Currency;

import com.chunarevsa.Website.Entity.Currency1;
import com.chunarevsa.Website.Exception.DublicateCurrency;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.CurrencyRepository;


public interface CurrencyService {
	void currencyIsPresent(long id, CurrencyRepository currencyRepository) throws NotFound;
	void activeValidate (long id, Currency1 currency) throws NotFound;
	void bodyIsNotEmpty (Currency1 bodyCurrency) throws FormIsEmpty;
	IdByJson getIdByJson (Currency1 bodyCurrency, CurrencyRepository currencyRepository) throws DublicateCurrency;
	Currency1 overrideItem (long id, Currency1 bodyCurrency, CurrencyRepository currencyRepository) throws DublicateCurrency;
} 