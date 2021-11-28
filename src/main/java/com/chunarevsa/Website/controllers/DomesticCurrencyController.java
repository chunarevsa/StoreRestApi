package com.chunarevsa.Website.controllers;

import javax.validation.Valid;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.dto.DomesticCurrencyRequest;
import com.chunarevsa.Website.service.DomesticCurrencyService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class DomesticCurrencyController {
	
	private final DomesticCurrencyService domesticCurrencyService;

	public DomesticCurrencyController (DomesticCurrencyService domesticCurrencyService) {
		this.domesticCurrencyService = domesticCurrencyService;
	}

	// Получение списка всех Currency с ограничением страницы (10)
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public Page<DomesticCurrency> getAll (@PageableDefault Pageable pageable) { 
		return domesticCurrencyService.getPage(pageable);
	} 

	// Получить по title
	@GetMapping("/{title}") //Проверка если вдруг выключена, то просто включить
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity getCurrencyByTitle (@PathVariable(value = "title") String title) { 
		
		return ResponseEntity.ok(domesticCurrencyService.getCurrencyDtoByTitle(title));
	}

	// Добавление 
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity createdCurrency (@Valid @RequestBody DomesticCurrencyRequest currencyRequest) throws AllException {
		
		return domesticCurrencyService.addCurrency(currencyRequest) // TODO: исключение
			.map(currency -> ResponseEntity.ok().body("Валюта добавлена")).orElseThrow();
	} 	
				
	 // Изменение
	@PutMapping("/edit/{title}") // TODO: сделать через параметр
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity editCurrency (@PathVariable(value = "title") String title, 
							@Valid @RequestBody DomesticCurrencyRequest currencyRequest) throws AllException {

		return ResponseEntity.ok(domesticCurrencyService.overrideCurrency(title, currencyRequest));
	} 

   // Удаление
	@DeleteMapping("/delete/{title}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deleteCurrency (@PathVariable(value = "title") String title) throws AllException {
		domesticCurrencyService.deleteCurrency(title);
		return ResponseEntity.ok("Валюта " + title + " была удалена");
	}	
}
