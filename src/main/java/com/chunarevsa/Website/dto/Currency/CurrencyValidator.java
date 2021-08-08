package com.chunarevsa.Website.dto.Currency;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.repo.CurrencyRepository;
import org.springframework.http.HttpStatus;

public class CurrencyValidator {

	// Проверка на наличие 
	public void currencyIsPresent (long id, CurrencyRepository currencyRepository) throws NotFound{
		Boolean currencyBoolean = currencyRepository.findById(id).isPresent();
		if (currencyBoolean == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}	 
	}

	// Проверка не выключен ли active = true
	public void activeValidate (long id, Currency currency) throws NotFound {
		if (currency.getActive() == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
	}

	public void bodyIsNotEmpty (Currency newCurrency) throws FormIsEmpty {
		if (newCurrency.getCode().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
	}

	public IdByJson getIdByJson (Currency newCurrency, CurrencyRepository currencyRepository) {
		currencyRepository.save(newCurrency);
		IdByJson idByJson = new IdByJson(newCurrency.getId());
		return idByJson;
	}

	public Currency overrideItem (long id, Currency bodyCurrency, CurrencyRepository currencyRepository) {
		Currency currency = currencyRepository.findById(id).orElseThrow();
		currency.setCode(bodyCurrency.getCode());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		currency.setActive(bodyCurrency.getActive());
		return currency;
	}

	
}
