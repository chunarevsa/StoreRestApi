package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.dto.IdDto;
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
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping
	public Page<Currency> currencyFindAll (@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Currency> pageCurrency =  currencyRepository.findByActive(true, pageable);

		return pageCurrency;
	}

	@GetMapping (path = "/{code}")
	public Currency currenciesFindByCode (@PathVariable(value = "code") String code) throws NotFound { 
		return currencyService.getCurrencyByCode(code);
	}

	// Получение по id
	@GetMapping (path = "/{id}")
	public Currency currencyMethod (@PathVariable(value = "id") long id) throws AllException { 
		return currencyService.getCurrency(id);
	} 

	// Добавление 
	@PostMapping
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Currency createdCurrency (@RequestBody Currency bodyCurrency) throws AllException {
		return currencyService.addCurrency(bodyCurrency);
	} 	
				
	 // Изменение
	@PutMapping(value = "/{id}")
	public Currency editCurrency (@PathVariable(value = "id") long id, @RequestBody Currency bodyCurrency) throws AllException {
		return currencyService.overrideCurrency(id, bodyCurrency);
	} 

   // Удаление
	@DeleteMapping(value = "/{id}")
	public ResponseEntity deleteCurrency (@PathVariable(value = "id") long id) throws AllException {
		return ResponseEntity.ok().body(id);
	}	
}
