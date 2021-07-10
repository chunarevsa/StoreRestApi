package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Currencies;
import com.chunarevsa.Website.repo.СurrenciesRepository;

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
public class СurrenciesControllerGUI {
	
	@Autowired
	private СurrenciesRepository currenciesRepository;
	public СurrenciesControllerGUI (СurrenciesRepository currenciesRepository) {
		this.currenciesRepository = currenciesRepository;
	}

	// Получение списка всех валют с ограничением страницы (10)
	@RequestMapping (path = "/currencies/gui", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Currencies> gamesFindAll (@PageableDefault(sort = { "id"}, direction = Sort.Direction.DESC) Pageable pageable) { 
		Page<Currencies> pageCurrencies = currenciesRepository.findAll(pageable);
		return pageCurrencies;
	}
	
	// Получение валюты по id
	@RequestMapping (path = "/currencies/gui/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Currencies gamesMethod (@PathVariable(value = "id") long id) { 
		Currencies currency = currenciesRepository.findById(id).orElseThrow();
		return currency;
	}

	// Добавление валюты
	@PostMapping(value = "/currencies/gui", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public long createdMerch (@RequestBody Currencies newMCurrency) {		
		currenciesRepository.save(newMCurrency);
		return newMCurrency.getId();
		} 

	// Изменение
	@PutMapping(value = "/currencies/gui/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Currencies editCurrency (@PathVariable(value = "id") long id, @RequestBody Currencies currencyhBody)	{
		Currencies currency = currenciesRepository.findById(id).orElseThrow();
		currency.setSku(currencyhBody.getSku());
		currency.setName(currencyhBody.getName());
		currency.setDescription(currencyhBody.getDescription());
		currency.setCost(currencyhBody.getCost());
		currenciesRepository.save(currency);
		return currency;
	} 

	/// Удаление
	@DeleteMapping(value = "/currencies/gui/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public long deleteMerch (@PathVariable(value = "id") long id)	{
		currenciesRepository.deleteById(id);
		return id;
	}

}
