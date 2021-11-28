package com.chunarevsa.Website.service;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.DublicateCurrency;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.chunarevsa.Website.service.inter.CurrencyServiceInterface;
import com.chunarevsa.Website.service.valid.CurrencyValid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

// TODO: Проверить логирование
@Service
public class CurrencyService implements CurrencyServiceInterface {

	private final CurrencyRepository currencyRepository;
	private final CurrencyValid currencyValid;


	public CurrencyService(
				CurrencyRepository currencyRepository, 
				CurrencyValid currencyValid) {
		this.currencyRepository = currencyRepository;
		this.currencyValid = currencyValid;
	}

	// Создание
	@Override
	public Currency addCurrency(Currency bodyCurrency) throws FormIsEmpty, DublicateCurrency {
		
		// Проверка на незаполеннные данные
		if (currencyValid.bodyIsEmpty(bodyCurrency)) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}

		// Проеврка на уникальность code
		if (currencyValid.codeIsPresent(bodyCurrency.getCode())) {
			throw new DublicateCurrency(HttpStatus.BAD_REQUEST);
		}

		// Включение (active = true) 
		bodyCurrency.setActive(true);
		Currency currency = currencyRepository.save(bodyCurrency);
		return currency;

	}

	// Получение по id
	@Override
	public Currency getCurrency (Long id) throws NotFound {
		// Проверка на наличие 
		if (!currencyValid.currencyIsPresent(id) ) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Выводим только в случае active = true
		if (!currencyValid.currencyIsActive(id)) {
			throw new NotFound(HttpStatus.NO_CONTENT);
		}

		return currencyRepository.findById(id).orElseThrow();
	}

	// Получение по code
	@Override
	public Currency getCurrencyByCode (String code) throws NotFound {
		// Проверка на наличие 
		if (!currencyValid.codeIsPresent(code) ) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		Currency currency = currencyRepository.findByCode(code);
		if (currency.isActive()) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		return currency;
	}

	// Запись параметров
	@Override
	public Currency overrideCurrency (long id, Currency bodyCurrency) throws DublicateCurrency, NotFound, FormIsEmpty {
		
		// Проверка на наличие 
		if (!currencyValid.currencyIsPresent(id) ) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		// Проверка на незаполеннные данные
		if (currencyValid.bodyIsEmpty(bodyCurrency)) {
			throw new FormIsEmpty(HttpStatus.BAD_REQUEST);
		}

		// Проеврка на уникальность code
		if (currencyValid.codeIsPresent(bodyCurrency.getCode())) {
			throw new DublicateCurrency(HttpStatus.BAD_REQUEST);
		}

		// Объединить проверки, потому что валюта может не поменяться, а поменяться только статус
		// Так выдаст ошибку, что такая валюта уже сущ.

		Currency currency = currencyRepository.findById(id).orElseThrow();
		currency.setCode(bodyCurrency.getCode());
		// Возможность вернуть удалённый (active = false) обратно (active = true)
		currency.setActive(bodyCurrency.isActive());

		return currencyRepository.save(currency);
	}

	@Override
	public void deleteCurrency(long id) throws NotFound {
		// Проверка на наличие 
		if (!currencyValid.currencyIsPresent(id) ) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		// Выводим только в случае active = true
		if (!currencyValid.currencyIsActive(id)) {
			throw new NotFound(HttpStatus.NO_CONTENT);
		}

		Currency currency = currencyRepository.findById(id).orElseThrow();
		currency.setActive(false);
		currencyRepository.save(currency);

	}

	// Id в JSON
	@Override
	public IdDto getIdByJson (Currency bodyCurrency) throws NotFound {
		// Проверка на наличие
		if (!currencyValid.currencyIsPresent(bodyCurrency.getId()) ) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}
		currencyRepository.save(bodyCurrency);

		return new IdDto(bodyCurrency.getId());
	}

	

	
}
