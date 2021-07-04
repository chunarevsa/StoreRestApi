package com.chunarevsa.Website.controllers;

import com.chunarevsa.Website.models.Currencies;
import com.chunarevsa.Website.repo.СurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class СurrenciesController {
	
	@Autowired
	private СurrenciesRepository currenciesRepository;

 	@GetMapping ("/сurrencies")
 	public String currenciesMain (Model model) {
		 Iterable<Currencies> currencies = currenciesRepository.findAll();
		 model.addAttribute("currencies", currencies); // Массив данных из таблицы
		 return "currencies-main";

 	}

	 @GetMapping ("/сurrencies/add")
	 public String currenciesAdd (Model model) {
		 return "сurrencies-add";
	 }

	 @PostMapping ("/сurrencies/add")
	 public String currenciesPostAdd (@RequestParam String name, @RequestParam String description, @RequestParam int cost, Model model) {
		 Currencies сurrency = new Currencies(name, description, cost);
		 сurrency.setType("Валюта");
		 currenciesRepository.save(сurrency);
		 return "redirect:/сurrencies/add";
	 }

}
