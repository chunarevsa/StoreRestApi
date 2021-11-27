package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.Entity.Currency;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.service.CurrencyService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/currency")
public class CurrencyController {
	
	private final CurrencyService currencyService;

	public CurrencyController (CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	// Получение списка всех Currency с ограничением страницы (10)
	/* @RequestMapping (path = "/currency", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Currency> currencyFindAll (@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)  Pageable pageable) { 
		// Сортировка по 10 элементов и только со значением active = true
		Page<Currency> pageCurrency =  currencyRepository.findByStatus(Status.ACTIVE, pageable);

		return pageCurrency;
	} */

	// Получение по id
	@GetMapping ("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity currencyMethod (@PathVariable(value = "id") Long id) throws AllException { 
		return ResponseEntity.ok(currencyService.getCurrency(id));
	} 

	// Получить по code
	@GetMapping("/code/{code}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity currenciesFindByCode (@PathVariable(value = "code") String code) throws NotFound { 
		return ResponseEntity.ok(currencyService.getCurrencyByCode(code));
	} 

	// Добавление 
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	//@ResponseStatus (value = HttpStatus.CREATED)	
	public ResponseEntity createdCurrency (@RequestBody Currency bodyCurrency) throws AllException {
		return ResponseEntity.ok(currencyService.addCurrency(bodyCurrency));
	} 	
				
	 // Изменение
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity editCurrency (@PathVariable(value = "id") Long id, @RequestBody Currency bodyCurrency) throws AllException {
		return ResponseEntity.ok(currencyService.overrideCurrency(id, bodyCurrency));
	} 

   // Удаление
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deleteCurrency (@PathVariable(value = "id") Long id) throws AllException {
		currencyService.deleteCurrency(id);
		return ResponseEntity.ok("Валюта " + id + " была удалена");
	}	
}
