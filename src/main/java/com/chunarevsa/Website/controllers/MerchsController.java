package com.chunarevsa.Website.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.chunarevsa.Website.models.Merchs;
import com.chunarevsa.Website.repo.MerchsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MerchsController {
	
	@Autowired
	private MerchsRepository merchsRepository;

 	@GetMapping ("/merch")
 	public String merchsMain (Model model) {
		 Iterable<Merchs> merchs = merchsRepository.findAll();
		 model.addAttribute("merchs", merchs); // Массив данных из таблицы
		 return "merch-main";

 	}

	@GetMapping ("/merch/add") // добавление мерча
 	public String merchAdd(Model model) {
		 return "merch-add";

 	} 

	@PostMapping ("/merch/add")
	public String merchsPostAdd(@RequestParam String name, @RequestParam String description, @RequestParam int cost, Model model) {
		Merchs merch = new Merchs(name, description, cost);
		merch.setType("Мерч");
		merchsRepository.save(merch);
		return "redirect:/merch";
	}

	// Обработчки Динамической ссылки
	@GetMapping ("/merch/{id}") 
 	public String merchsDetails (@PathVariable(value = "id") long id, Model model) {
		  if (!merchsRepository.existsById(id)){ 
			return "redirect:/merch";
		  } 
		  Optional<Merchs> merch = merchsRepository.findById(id);
		  ArrayList<Merchs> res = new ArrayList<>();
		  merch.ifPresent(res::add);
		  model.addAttribute("merch", res);
		  return "merch-details";
 	}
	 
}
