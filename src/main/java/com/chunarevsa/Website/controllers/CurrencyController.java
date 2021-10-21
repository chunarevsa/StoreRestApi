package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.IdDto;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.chunarevsa.Website.service.CurrencyService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping (path = "/currency", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {
	
	private final CurrencyRepository currencyRepository;
	private final CurrencyService currencyService;

	public CurrencyController (CurrencyRepository currencyRepository, CurrencyService currencyService) {
		this.currencyRepository = currencyRepository;
		this.currencyService = currencyService;
	}

	// Получение списка всех Currency с ограничением страницы (10)
	@RequestMapping (method = RequestMethod.GET)
	public Page<Currency> currencyFindAll (@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Currency> pageCurrency =  currencyRepository.findByActive(true, pageable);

		return pageCurrency;
	}

	@RequestMapping (path = "/{code}", method = RequestMethod.GET)
	public Currency currenciesFindByCodeSet (@PathVariable(value = "code") String code) { 
		// Сортировка по 10 элементов и только со значением active = true
		Currency setCurrency =  currencyRepository.findByCode(code);
		
		return setCurrency;
	}

	// Получение по id
	@RequestMapping (path = "/{id}", method = RequestMethod.GET)
	public Currency currencyMethod (@PathVariable(value = "id") long id) throws AllException { 
		// Проверка на наличие 
		currencyService.currencyIsPresent(id, currencyRepository);
		Currency currency = currencyRepository.findById(id).orElseThrow();
		// Вывести только в случае active = true
		currencyService.activeValidate(currency.getId(), currency);
		return currency;
	} 

	// Добавление 
	@PostMapping
	@ResponseStatus (value = HttpStatus.CREATED)	
	public IdDto createdCurrency (@RequestBody Currency bodyCurrency) throws AllException {
		currencyService.bodyIsNotEmpty(bodyCurrency);
		// Включение (active = true) 
		bodyCurrency.setActive(true);
		// Представление Id в JSON
		return currencyService.getIdByJson(bodyCurrency, currencyRepository);
	} 	
				
	 // Изменение
	@PutMapping(value = "/{id}")
	public Currency editCurrency (@PathVariable(value = "id") long id, @RequestBody Currency bodyCurrency) throws AllException {
		// Проверка на наличие 
		currencyService.currencyIsPresent(id, currencyRepository);
		// Проверка на запленные данные
		currencyService.bodyIsNotEmpty(bodyCurrency);
		// Запись параметров
		Currency currency = currencyService.overrideItem(id, bodyCurrency, currencyRepository);
		return currency;
	} 

   // Удаление
	@DeleteMapping(value = "/{id}")
	public Long deleteCurrency (@PathVariable(value = "id") long id) throws AllException {
		// Проверка на наличие
		currencyService.currencyIsPresent(id, currencyRepository);
		Currency currency = currencyRepository.findById(id).orElseThrow();
		// Проверка не выключен ли active = true
		currencyService.activeValidate(currency.getId(), currency);
		// Выключение active = false
		currency.setActive(false);
		currencyRepository.save(currency);
		// Вывод об успешном удалении
		return id;
	}
}
