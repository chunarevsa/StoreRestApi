package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Currencies;
import com.chunarevsa.Website.repo.СurrenciesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class СurrenciesController {
	
	@Autowired
	private СurrenciesRepository currenciesRepository;

	// Получение списка всех валют
	@RequestMapping (path = "/currencies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Currencies> currenciesMethod () { 
		Iterable<Currencies> currencies = currenciesRepository.findAll();
		return currencies;
	} 

	// Добавление валюты
	@PostMapping(value = "/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus (value = HttpStatus.CREATED)	
	public Currencies createdMerch (@RequestBody Currencies newMCurrency) {		
		return currenciesRepository.save(newMCurrency);
		} 

	// Изменение
	@PutMapping(value = "/currencies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	@DeleteMapping(value = "/currencies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public long deleteMerch (@PathVariable(value = "id") long id)	{
		currenciesRepository.deleteById(id);
		return id;
	}

}
