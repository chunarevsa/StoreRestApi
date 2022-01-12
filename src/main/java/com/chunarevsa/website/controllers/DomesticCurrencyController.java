package com.chunarevsa.website.controllers;

import javax.validation.Valid;

import com.chunarevsa.website.payload.ApiResponse;
import com.chunarevsa.website.payload.DomesticCurrencyRequest;
import com.chunarevsa.website.security.jwt.JwtUser;
import com.chunarevsa.website.service.CurrencyService;

import org.springframework.data.domain.Pageable;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/currency")
@Api(value = "Currency Rest API", description = "Внутренняя валюта (gold, silver...)")
public class DomesticCurrencyController {
	
	private final CurrencyService domesticCurrencyService;

	public DomesticCurrencyController (CurrencyService domesticCurrencyService) {
		this.domesticCurrencyService = domesticCurrencyService;
	}

	/**
	 * Получение всех Currency
	 * Если ADMIN -> page Currencies, если USER -> set CurrenciesDto
	 * @param pageable
	 * @param jwtUser
	 * @return
	 */
	@GetMapping("/all")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получение всех Currency. Формат ответа зависить от роли")
	public ResponseEntity getCurrencies(@ApiIgnore Pageable pageable,
				@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) { 

		return  ResponseEntity.ok().body(domesticCurrencyService.getCurrencies(pageable, jwtUser));
	}

	/**
	 * Получить Currency
	 * Если ADMIN -> Currency, если USER ->  CurrencyDto
	 * @param title
	 * @param jwtUser
	 * @return
	 */
	@GetMapping("/{title}") 
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Получить Currency. Формат ответа зависить от роли")
	public ResponseEntity getCurrencyByTitle(@PathVariable(value = "title") String title,
		@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) { 

		return ResponseEntity.ok().body(domesticCurrencyService.getCurrency(title ,jwtUser));
	}

	/**
	 * Покупка Currency
	 * @param title
	 * @param amount
	 * @param jwtUser
	 * @return
	 */
	@PostMapping("/buy")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Покупка внутренней валюты за $ ")
	public ResponseEntity buyCurrency(@RequestParam String title,
					@RequestParam String amount,
					@ApiIgnore @AuthenticationPrincipal JwtUser jwtUser) {

		return ResponseEntity.ok().body(domesticCurrencyService.buyCurrency(title, amount, jwtUser));
	} 

	/**
	 * Добавление Currency
	 * @param currencyRequest
	 * @return
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Добавление Currency")
	public ResponseEntity addCurrency(@Valid @RequestBody DomesticCurrencyRequest currencyRequest)  {

		return ResponseEntity.ok().body(domesticCurrencyService.addCurrency(currencyRequest));
	} 	
	
	/**
	 * Изменение Currency
	 * @param title
	 * @param currencyRequest
	 * @return
	 */
	@PutMapping("/{title}/edit") 
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Изменение Currency")
	public ResponseEntity editCurrency (@PathVariable(value = "title") String title, 
				@Valid @RequestBody DomesticCurrencyRequest currencyRequest) {

		return ResponseEntity.ok().body(domesticCurrencyService.editCurrency(title, currencyRequest));
	} 

	/**
	 * Получение своего инвенторя
	 * Вся информация выдаётся через DTO
	 * @param title
	 * @return
	 */
	@DeleteMapping("/{title}/delete")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Удаление валюты ")
	public ResponseEntity deleteCurrency (@PathVariable(value = "title") String title) {
		domesticCurrencyService.deleteCurrency(title);
		return ResponseEntity.ok(new ApiResponse(true, "Валюта " + title + " была удалена"));
	}	

}
