package com.chunarevsa.Website.service;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.DublicateCurrency;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.chunarevsa.Website.service.inter.CurrencyServiceInterface;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService implements CurrencyServiceInterface {

	// Проверка на наличие
	@Override 
	public void currencyIsPresent (long id, CurrencyRepository currencyRepository) throws NotFound{
		Boolean currencyBoolean = currencyRepository.findById(id).isPresent();
		if (currencyBoolean == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}	 
	}

	// Проверка не выключен ли active = true
	@Override
	public void activeValidate (long id, Currency currency) throws NotFound {
		if (currency.getActive() == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
	}

	// Проверка на незаполеннные данные
	@Override
	public void bodyIsNotEmpty (Currency bodyCurrency) throws FormIsEmpty {
		if (bodyCurrency.getCode().isEmpty() == true) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}
	}

	// Представление Id в JSON
	@Override
	public IdDto getIdByJson (Currency bodyCurrency, CurrencyRepository currencyRepository) throws DublicateCurrency {
		try {
			currencyRepository.save(bodyCurrency);
		} catch (Exception e) {
			throw new DublicateCurrency();
		}
		IdDto idByJson = new IdDto(bodyCurrency.getId());
		return idByJson;
	}

	// Запись параметров
	@Override
	public Currency overrideItem (long id, Currency bodyCurrency, CurrencyRepository currencyRepository) throws DublicateCurrency {
		Currency currency = currencyRepository.findById(id).orElseThrow();
		currency.setCode(bodyCurrency.getCode());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		currency.setActive(bodyCurrency.getActive());
		try {
			currencyRepository.save(currency);
		} catch (Exception e) {
			throw new DublicateCurrency();
		}
		return currency;
	}

	
}
