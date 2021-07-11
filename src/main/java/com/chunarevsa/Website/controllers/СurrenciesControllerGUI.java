package com.chunarevsa.Website.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.models.Currencies;
import com.chunarevsa.Website.repo.СurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class СurrenciesControllerGUI {
	
	@Autowired
	private СurrenciesRepository currenciesRepository;
	public СurrenciesControllerGUI (СurrenciesRepository currenciesRepository) {
		this.currenciesRepository = currenciesRepository;
	}

	// Показать весь список валют
 	@GetMapping ("/currencies/gui")
 	public String currenciesMain (Model model) {
		 Iterable<Currencies> currencies = currenciesRepository.findAll();
		 model.addAttribute("currencies", currencies); 
		 return "currencies-main";
 	}

	 // Добавление валюты
	 @GetMapping ("/currencies/gui/add")
	 public String currenciesAdd (Model model) {
		 return "currencies-add";
	 }
	 @PostMapping ("/currencies/gui/add")
	 public String currenciesPostAdd (@RequestParam String sku, @RequestParam String name, @RequestParam String type, @RequestParam String description, @RequestParam int cost, Model model) {
		 Currencies сurrency = new Currencies(sku, name, type, description, cost);
		 currenciesRepository.save(сurrency);
		 return "redirect:/currencies/gui";
	 }

	 // Обработчки Динамической ссылки
	@GetMapping ("/currencies/gui/{id}") 
	public String currenciesDetails (@PathVariable(value = "id") long id, Model model) {
		 if (!currenciesRepository.existsById(id)){ 
		  return "redirect:/currencies/gui";
		 } 
		 Optional<Currencies> сurrency = currenciesRepository.findById(id);
		 ArrayList<Currencies> res = new ArrayList<>();
		 сurrency.ifPresent(res::add);
		 model.addAttribute("currency", res);
		 return "currencies-details";
	}

	// Изменение
	@GetMapping ("/currencies/gui/{id}/edit") 
	public String currenciesEdit (@PathVariable(value = "id") long id, Model model) {
		 if (!currenciesRepository.existsById(id)){ 
		  return "redirect:/currencies/gui";
		 } 
		 Optional<Currencies> currency = currenciesRepository.findById(id);
		 ArrayList<Currencies> res = new ArrayList<>();
		 currency.ifPresent(res::add);
		 model.addAttribute("currency", res);
		 return "currencies-edit";
	}
  @PostMapping ("/currencies/gui/{id}/edit") 
  public String currenciesPostUpdate (@PathVariable(value = "id") long id, @RequestParam String sku, @RequestParam String name, @RequestParam String type, @RequestParam String description, @RequestParam int cost, Model model) {
	  Currencies currency = currenciesRepository.findById(id).orElseThrow();
	  currency.setType(type);
	  currency.setSku(sku);
	  currency.setName(name);
	  currency.setDescription(description);
	  currency.setCost(cost);
	  currenciesRepository.save(currency);
	  return "redirect:/currencies/gui";
  }

  // Удаление 
	@PostMapping ("/currencies/gui/{id}/remove") 
	public String currenciesPostDelete (@PathVariable(value = "id") long id, Model model) {
		Currencies currency = currenciesRepository.findById(id).orElseThrow();
		currenciesRepository.delete(currency);
		return "redirect:/currencies/gui";
	}
}
