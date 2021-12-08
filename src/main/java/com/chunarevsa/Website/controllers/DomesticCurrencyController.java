package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.payload.DomesticCurrencyRequest;
import com.chunarevsa.Website.security.jwt.JwtUser;
import com.chunarevsa.Website.service.DomesticCurrencyService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Внутренняя валюта gold, silver..
 * 
 */
@RestController
@RequestMapping("/currency")
public class DomesticCurrencyController {
	
	private final DomesticCurrencyService domesticCurrencyService;

	public DomesticCurrencyController (DomesticCurrencyService domesticCurrencyService) {
		this.domesticCurrencyService = domesticCurrencyService;
	}

	/**
	 * Получение всех Currency
	 * Если ADMIN -> page Currencies, если USER -> set CurrenciesDto
	 * @param pageable
	 * @param jwtUser
	 */
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getCurrencies(@PageableDefault Pageable pageable,
				@AuthenticationPrincipal JwtUser jwtUser) { 

		return  ResponseEntity.ok().body(domesticCurrencyService.getCurrencies(pageable, jwtUser));
	}

	/**
	 * Получить Currency
	 * Если ADMIN -> Currency, если USER ->  CurrencyDto
	 * @param title
	 * @param jwtUser
	 */
	@GetMapping("/{title}") //Проверка если вдруг выключена, то просто включить
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getCurrencyByTitle(@PathVariable(value = "title") String title,
				@AuthenticationPrincipal JwtUser jwtUser) { 

		return ResponseEntity.ok(domesticCurrencyService.getCurrency(title ,jwtUser));
	}

	/**
	 * Покупка Currency
	 * @param title
	 * @param amount
	 * @param jwtUser
	 */
	@PostMapping("/buy")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity buyCurrency(@RequestParam String title,
					@RequestParam String amount,
					@AuthenticationPrincipal JwtUser jwtUser) {
		
		return ResponseEntity.ok().body(domesticCurrencyService.buyCurrency(title, amount, jwtUser));
	} 

	/**
	 * Добавление Currency
	 * @param currencyRequest
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity addCurrency(@Valid @RequestBody DomesticCurrencyRequest currencyRequest) throws AllException {

		return domesticCurrencyService.addCurrency(currencyRequest) // TODO: исключение
			.map(currency -> ResponseEntity.ok().body("Валюта добавлена")).orElseThrow();
	} 	
	
	/**
	 * Изменение Currency
	 * @param title
	 * @param currencyRequest

	 */
	@PutMapping("/{title}/edit") // TODO: сделать через параметр
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity editCurrency (@PathVariable(value = "title") String title, 
				@Valid @RequestBody DomesticCurrencyRequest currencyRequest) throws AllException {

		return ResponseEntity.ok(domesticCurrencyService.editCurrency(title, currencyRequest));
	} 

	/**
	 * Удаление
	 * @param title
	 */
	@DeleteMapping("/{title}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deleteCurrency (@PathVariable(value = "title") String title) throws AllException {
		domesticCurrencyService.deleteCurrency(title);
		return ResponseEntity.ok("Валюта " + title + " была удалена");
	}	

}