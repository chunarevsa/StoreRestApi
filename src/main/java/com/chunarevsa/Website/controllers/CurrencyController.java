package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Entity.Status;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.chunarevsa.Website.service.CurrencyService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyController {
	
	private final CurrencyRepository currencyRepository;
	private final CurrencyService currencyService;

	public CurrencyController (
				CurrencyRepository currencyRepository, 
				CurrencyService currencyService) {
		this.currencyRepository = currencyRepository;
		this.currencyService = currencyService;
	}

	// Получение списка всех Currency с ограничением страницы (10)
	@RequestMapping (path = "/currency", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Currency> currencyFindAll (@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Currency> pageCurrency =  currencyRepository.findByStatus(Status.ACTIVE, pageable);

		return pageCurrency;
	}

	// Получение по id
	@RequestMapping (path = "/currency/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Currency currencyMethod (@PathVariable(value = "id") Long id) throws AllException { 
		return currencyService.getCurrency(id);
	} 

	// Получить по code
	@RequestMapping (path = "/currency/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Currency currenciesFindByCode (@PathVariable(value = "code") String code) throws NotFound { 
		return currencyService.getCurrencyByCode(code);
	} 

	// Добавление 
	@PostMapping (value = "/currency", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Currency createdCurrency (@RequestBody Currency bodyCurrency) throws AllException {
		return currencyService.addCurrency(bodyCurrency);
	} 	
				
	 // Изменение
	@PutMapping(value = "/currency/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Currency editCurrency (@PathVariable(value = "id") Long id, @RequestBody Currency bodyCurrency) throws AllException {
		return currencyService.overrideCurrency(id, bodyCurrency);
	} 

   // Удаление
	@DeleteMapping(value = "/currency/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteCurrency (@PathVariable(value = "id") Long id) throws AllException {
		return ResponseEntity.ok().body(id);
	}	
}
