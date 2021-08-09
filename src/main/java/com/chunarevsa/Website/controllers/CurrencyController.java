package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.IdByJson;
import com.chunarevsa.Website.dto.Response;
import com.chunarevsa.Website.dto.Currency.CurrencyValidator;
import com.chunarevsa.Website.repo.CurrencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CurrencyController {
	
	@Autowired
	private CurrencyRepository currencyRepository;
	@Autowired
	private CurrencyValidator currencyValidator;
	public CurrencyController (CurrencyRepository currencyRepository, CurrencyValidator currencyValidator) {
		this.currencyRepository = currencyRepository;
		this.currencyValidator = currencyValidator;
	}

	// Получение списка всех Currency с ограничением страницы (10)
	@RequestMapping (path = "/currency", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Currency> currencyFindAll (@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Currency> pageCurrency =  currencyRepository.findByActive(true, pageable);
		return pageCurrency;
	}

	// Получение по id
	@RequestMapping (path = "/currency/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Currency currencyMethod (@PathVariable(value = "id") long id) throws AllException { 
		// Проверка на наличие 
		currencyValidator.currencyIsPresent(id, currencyRepository);
		Currency currency = currencyRepository.findById(id).orElseThrow();
		// Вывести только в случае active = true
		currencyValidator.activeValidate(currency.getId(), currency);
		return currency;
	} 

	// Добавление 
	@PostMapping(value = "/currency", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public IdByJson createdCurrency (@RequestBody Currency bodyCurrency) throws AllException {
		currencyValidator.bodyIsNotEmpty(bodyCurrency);
		// Включение (active = true) 
		bodyCurrency.setActive(true);
		// Представление Id в JSON
		return currencyValidator.getIdByJson(bodyCurrency, currencyRepository);
	} 	
				
	 // Изменение
	@PutMapping(value = "/currency/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Currency editCurrency (@PathVariable(value = "id") long id, @RequestBody Currency bodyCurrency) throws AllException {
		// Проверка на наличие 
		currencyValidator.currencyIsPresent(id, currencyRepository);
		// Проверка на запленные данные
		currencyValidator.bodyIsNotEmpty(bodyCurrency);
		// Запись параметров
		Currency currency = currencyValidator.overrideItem(id, bodyCurrency, currencyRepository);
		return currency;
	} 

   // Удаление
	@DeleteMapping(value = "/currency/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteCurrency (@PathVariable(value = "id") long id) throws AllException {
		// Проверка на наличие
		currencyValidator.currencyIsPresent(id, currencyRepository);
		Currency currency = currencyRepository.findById(id).orElseThrow();
		// Проверка не выключен ли active = true
		currencyValidator.activeValidate(currency.getId(), currency);
		// Выключение active = false
		currency.setActive(false);
		currencyRepository.save(currency);
		// Вывод об успешном удалении
		Response response = new Response(currency.getActive());
		return response;
	}
}
