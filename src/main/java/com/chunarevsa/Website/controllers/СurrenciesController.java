package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Currencies;
import com.chunarevsa.Website.repo.СurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class СurrenciesController {
	
	@Autowired
	private СurrenciesRepository currenciesRepository;

 	@GetMapping ("/сurrencies")
 	public String currenciesgMain (Model model) {
		 Iterable<Currencies> currencies = currenciesRepository.findAll();
		 model.addAttribute("currencies", currencies); // Массив данных из таблицы
		 return "currencies-main";

 	}
}
